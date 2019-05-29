package data;

import javax.xml.bind.JAXBElement;
import java.io.File;
import java.util.Collection;
import java.util.LinkedList;

import static data.Util.createTemplate;

public class CQCurrentRecords {

    private boolean hasUnsavedCQs;
    RecordList questions;
    private File f;

    public CQCurrentRecords() {
        questions =CQXMLFileHandler.xmlObjectCreator.createRecordList();
        hasUnsavedCQs = false;
    }

    public CQCurrentRecords(File fileWithQuestions) {
        this();
        this.f = fileWithQuestions;
        if (f != null) {
            try {
                questions = CQXMLFileHandler.read(fileWithQuestions);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public boolean hasUnSavedQs() {
        return hasUnsavedCQs;
    }

    public void setHasUnsavedCQs(boolean hasUnsavedCQs) {
        this.hasUnsavedCQs = hasUnsavedCQs;
    }

    public RecordList getRecordList() {
        return questions;
    }

    public Collection<CQ> getCQs() {
        Collection<CQ> result = new LinkedList<>();
        for (JAXBElement<?> element : questions.getCQOrCQTemplate()) {
            Object cq = element.getValue();
            if (cq instanceof CQ) {
                result.add((CQ) cq);
            }
        }
        return result;
    }


    public void removeCQ(String question) {
        if (questions.cqOrCQTemplate != null) {
            questions.cqOrCQTemplate.removeIf(cq -> cq.getValue() instanceof CQ && ((CQ) cq.getValue()).getValue().equals(question));
        }
    }

    public boolean isEmpty() {
        return questions.getCQOrCQTemplate().isEmpty();
    }

    public void addCQ(String question, CQTemplate template) {
        CQ cq = new CQ();
        if (questions.cqOrCQTemplate == null) {
            questions.cqOrCQTemplate = new LinkedList<JAXBElement<?>>();
        }
        //TODO: Need to implement assigning id's to new questions
        cq.setValue(question);
        if (template!= null) {
            if (cq.ontologyOrCQTemplate == null) {
                cq.ontologyOrCQTemplate = new LinkedList<JAXBElement<?>>();
            }
            cq.ontologyOrCQTemplate.add(CQXMLFileHandler.xmlObjectCreator.createCQCQTemplate(template));
        }
        questions.cqOrCQTemplate.add(CQXMLFileHandler.xmlObjectCreator.createCQ(cq));
    }

    public void addCQ(String question, String template) {
        if (template!= null && !template.isEmpty()) {
            CQTemplate templ = createTemplate(template);
            addCQ(question, templ);
        }
    }

    public void addCQ(CQ question) {
        questions.cqOrCQTemplate.add(CQXMLFileHandler.xmlObjectCreator.createCQ(question));
    }

    public void setFile(File f) {
        this.f = f;
    }

    public File getFile() {
        return f;
    }

    public boolean hasFile () {
        return f != null;
    }

    public String getFilename() {
        return getFile().getName();
    }
}
