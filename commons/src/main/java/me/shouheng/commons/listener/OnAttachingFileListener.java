package me.shouheng.commons.listener;

import me.shouheng.commons.model.AttachmentFile;

public interface OnAttachingFileListener {

    void onAttachingFileErrorOccurred(AttachmentFile attachment);

    void onAttachingFileFinished(AttachmentFile attachment);
}
