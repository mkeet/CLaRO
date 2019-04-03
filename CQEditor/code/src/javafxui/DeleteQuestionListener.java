package javafxui;

import messages.DeleteOldQuestionEvent;

public interface DeleteQuestionListener {
    void onOldQuestionDeleted(DeleteOldQuestionEvent e);
}
