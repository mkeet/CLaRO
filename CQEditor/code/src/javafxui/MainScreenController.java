package javafxui;

import data.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import messages.*;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import javax.xml.bind.JAXBElement;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class MainScreenController implements Initializable, DeleteQuestionListener {

    private AutoCompletionBinding binding;
    private boolean STRICT_ORDERED_MATCH = true;
    private List<CQTemplate> possibleSuggestions;
    private CQTemplate lastSelectedTemplate;

    private CQCurrentRecords openRecords = null;
    private ObservableList<String> listViewItems = FXCollections.observableArrayList();

    private FileChooser fileChooser = new FileChooser();

    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> lastScheduledTask;

    @ FXML
    private TextField cqTextField;
    @FXML
    private MenuItem openFileMenuitem;
    @FXML
    private MenuItem closeFileMenuItem;
    @FXML
    private MenuItem saveFileMenuItem;
    @FXML
    private MenuItem exitMenuItem;
    @FXML
    private CheckMenuItem startsWithMenuItem;
    @FXML
    private ListView existingCQListView;
    @FXML
    private Label infoLabel;

    private KeyCodeCombination controlOpen = new KeyCodeCombination(KeyCode.O, KeyCombination.SHORTCUT_ANY);
    private KeyCodeCombination controlSave = new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_ANY);
    //private KeyCodeCombination controlClose = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN);


    EventHandler configStartsWithSuggestionHandler = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent t) {
            if (startsWithMenuItem.isSelected()) {
                STRICT_ORDERED_MATCH = true;
            } else {
                STRICT_ORDERED_MATCH = false;
            }
            onToggleStartsWithConfig();
        }
    };

    EventHandler openFilehandler = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent t) {
            if (openRecords!= null && openRecords.hasUnSavedQs()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Disregard open file?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    File chosenFile = fileChooser.showOpenDialog(null);
                    if (chosenFile != null) {
                        onCloseCurrentFile(new CloseFileEvent(""));
                        openRecords = new CQCurrentRecords(chosenFile);
                        onOpenNewFile(new OpenFileEvent(chosenFile.getName()));
                    }
                }
            } else {
                File chosenFile = fileChooser.showOpenDialog(null);
                if (chosenFile != null) {
                    openRecords = new CQCurrentRecords(chosenFile);
                    onOpenNewFile(new OpenFileEvent(chosenFile.getName()));
                }
            }
        }
    };

    EventHandler saveFilehandler = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent t) {
            File fileToBeSaved = openRecords != null ? openRecords.getFile() : null;
            if (fileToBeSaved == null) {
                fileToBeSaved = fileChooser.showSaveDialog(null);
            }

            if (fileToBeSaved!=null && openRecords !=null && openRecords.hasUnSavedQs()) {
                try {

                    String textInTextBox = cqTextField.getText().trim();
                    if (!textInTextBox.isEmpty() && textInTextBox.endsWith("?") && !textInTextBox.contains(CONFIG.NOUN_PHRASE_PLACEHOLDER) && !textInTextBox.contains(CONFIG.VERB_PHRASE_PLACEHOLDER)) {
                        openRecords.addCQ(textInTextBox, lastSelectedTemplate);
                    }

                    CQXMLFileHandler.write(fileToBeSaved, openRecords.getRecordList());

                    openRecords.setHasUnsavedCQs(false);
                    if (!openRecords.hasFile()) {
                        openRecords.setFile(fileToBeSaved);
                    }

                    onSaveCurrentFile(new SaveFileEvent(openRecords.hasFile()? openRecords.getFilename():fileToBeSaved.getName()));
                    onInformUser(new InformUserEvent("Saved file."));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };


    EventHandler closeFilehandler = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent t) {
            if (openRecords!=null) {
                if (openRecords.hasUnSavedQs()) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Disregard changes in file?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
                    alert.showAndWait();
                    if (alert.getResult() == ButtonType.YES) {
                        String filename = "Untitled";
                        if (openRecords.hasFile()) {
                            filename = openRecords.getFilename();
                        }
                        onCloseCurrentFile(new CloseFileEvent(filename));
                    }
                } else {
                    String filename = "Untitled";
                    if (openRecords.hasFile()) {
                        filename = openRecords.getFilename();
                    }
                    onCloseCurrentFile(new CloseFileEvent(filename));
                }
            }
        }
    };

    EventHandler exitFilehandler = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent t) {
            if (openRecords == null) {
                System.exit(0);
            } else if (openRecords!=null && !openRecords.hasUnSavedQs()) {
                System.exit(0);
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Disregard open file?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    System.exit(0);
                }
            }
        }
    };

    EventHandler addQuestionHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent keyEvent) {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                String typedQuestion = cqTextField.getText().trim();
                if (!typedQuestion.endsWith("?")) {
                    onInformUser(new InformUserEvent("Questions end with ?"));
                } else if (typedQuestion.contains(CONFIG.NOUN_PHRASE_PLACEHOLDER)) {
                    onInformUser(new InformUserEvent("Invalid question due to presence of noun phrase placeholder"));
                } else if (typedQuestion.contains(CONFIG.VERB_PHRASE_PLACEHOLDER)) {
                    onInformUser(new InformUserEvent("Invalid question due to presence of verb phrase placeholder"));
                } else {
                    onNewQuestionAdded(new AddNewQuestionEvent(typedQuestion));
                }
            }
        }
    };

    EventHandler windowCloseHandler = new EventHandler<WindowEvent>() {
        @Override
        public void handle(WindowEvent windowEvent) {
            executor.shutdown();
        }
    };

    EventHandler chooseTemplateHandler = new EventHandler<AutoCompletionBinding.AutoCompletionEvent<CQTemplate>>() {
        @Override
        public void handle(AutoCompletionBinding.AutoCompletionEvent<CQTemplate> stringAutoCompletionEvent) {
            lastSelectedTemplate = stringAutoCompletionEvent.getCompletion();
        }
    };

    ChangeListener<Number> cqTextFieldWidthChangeListener = new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
            if (binding !=null) {
                binding.setPrefWidth(cqTextField.getWidth());
            }
        }
    };


    Callback<ListView<String>, ListCell<String>> listviewCellFactor = new Callback<ListView<String>, ListCell<String>>() {
        @Override
        public ListCell<String> call(ListView<String> param) {
            return new CQListCell(MainScreenController.this);
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        possibleSuggestions = new LinkedList<>();

        try {
            InputStream inStream = getClass().getClassLoader().getResourceAsStream(CONFIG.CLARO_TEMPLATE_FILENAME);
            RecordList recordList = CQXMLFileHandler.read(inStream);
            for (JAXBElement<?> element: recordList.getCQOrCQTemplate()) {
                Object item = element.getValue();
                if (item instanceof CQTemplate) {
                    possibleSuggestions.add((CQTemplate) item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        cqTextField.setOnKeyPressed(addQuestionHandler);
        cqTextField.getParent().requestFocus();
        openFileMenuitem.setOnAction(openFilehandler);
        openFileMenuitem.setAccelerator(controlOpen);
        closeFileMenuItem.setOnAction(closeFilehandler);
        startsWithMenuItem.setOnAction(configStartsWithSuggestionHandler);
        //TODO: find out how to deal with this bug regarding accelerator for close button
        saveFileMenuItem.setOnAction(saveFilehandler);
        saveFileMenuItem.setAccelerator(controlSave);
        exitMenuItem.setOnAction(exitFilehandler);
        JFXCQLaucher.JFXCQLaucherStage.setOnCloseRequest(windowCloseHandler);
        cqTextField.widthProperty().addListener(cqTextFieldWidthChangeListener);
        existingCQListView.setItems(listViewItems);
        existingCQListView.setCellFactory(listviewCellFactor);
        openRecords = new CQCurrentRecords();
        onToggleStartsWithConfig();
    }


    public void onToggleStartsWithConfig() {
        if (binding !=null) {
            binding.dispose();
        }
        if (STRICT_ORDERED_MATCH) {
            binding = TextFields.bindAutoCompletion(cqTextField, sr -> {
                List<CQTemplate> matchingList = new LinkedList<>();
                for (CQTemplate possibleSuggestion : possibleSuggestions) {
                    if (!sr.getUserText().trim().isEmpty() && possibleSuggestion.toString().toLowerCase().startsWith(sr.getUserText().toLowerCase())) {
                        matchingList.add(possibleSuggestion);
                    }
                }
                return matchingList;
            });
            binding.setOnAutoCompleted(chooseTemplateHandler);
        } else {
            binding = TextFields.bindAutoCompletion(cqTextField, possibleSuggestions);
            binding.setOnAutoCompleted(chooseTemplateHandler);
        }
        binding.setPrefWidth(cqTextField.getWidth());
    }

    public void onInformUser(InformUserEvent e) {
        infoLabel.setText(e.getMessage());

        if (lastScheduledTask ==null || lastScheduledTask.isDone()) {
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        infoLabel.setText("");
                    });

                }
            };
            lastScheduledTask = executor.schedule(task, 2, TimeUnit.SECONDS);
        }
    }

    public void onNewQuestionAdded(AddNewQuestionEvent e) {
        cqTextField.setText("");

        listViewItems.add(e.getQuestion());
        openRecords.addCQ(e.getQuestion(), lastSelectedTemplate);
        openRecords.setHasUnsavedCQs(true);

        if (openRecords.hasFile()) {
            JFXCQLaucher.JFXCQLaucherStage.setTitle("*" + openRecords.getFilename());
        } else {
            JFXCQLaucher.JFXCQLaucherStage.setTitle("*Untitled");
        }

        onOpenRecordsChange();
    }


    public void onOldQuestionDeleted(DeleteOldQuestionEvent e) {
        listViewItems.remove(e.getQuestion());
        openRecords.removeCQ(e.getQuestion());
        openRecords.setHasUnsavedCQs(true);

        if (openRecords.hasFile()) {
            JFXCQLaucher.JFXCQLaucherStage.setTitle("*" + openRecords.getFilename());
        } else {
            JFXCQLaucher.JFXCQLaucherStage.setTitle("*Untitled");
        }
        onOpenRecordsChange();
    }

    public void onOpenNewFile(OpenFileEvent e) {
        List<String> questionsAsString = new LinkedList<>();
        for (CQ cq : openRecords.getCQs()) {
            questionsAsString.add(cq.getValue());
        }
        listViewItems.addAll(questionsAsString);
        JFXCQLaucher.JFXCQLaucherStage.setTitle(e.getFilename());
        closeFileMenuItem.setVisible(true);
        onOpenRecordsChange();
    }

    public void onCloseCurrentFile(CloseFileEvent e) {
        openRecords = new CQCurrentRecords();
        listViewItems.clear();
        JFXCQLaucher.JFXCQLaucherStage.setTitle("");
        closeFileMenuItem.setVisible(false);
        onOpenRecordsChange();
    }

    public void onSaveCurrentFile(SaveFileEvent e) {
        JFXCQLaucher.JFXCQLaucherStage.setTitle(e.getFilename());

        onOpenRecordsChange();
    }

    public void onOpenRecordsChange() {

        if (listViewItems.isEmpty() && openRecords.isEmpty()) {
            existingCQListView.setVisible(false);
        } else {
            existingCQListView.setVisible(true);
        }

        if (openRecords.hasUnSavedQs()) {
            closeFileMenuItem.setVisible(true);
        }

        if (openRecords!=null && !openRecords.hasFile() && listViewItems.size()==0) {
            openRecords = new CQCurrentRecords();
            listViewItems.clear();
            JFXCQLaucher.JFXCQLaucherStage.setTitle("");
            closeFileMenuItem.setVisible(false);
        }
        lastSelectedTemplate = null;

    }

}
