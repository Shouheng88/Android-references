package me.shouheng.commons.manager;

import me.shouheng.commons.model.AttachmentFile;

/**
 * Created by wangshouheng on 2017/11/17. */
public class ModelFactory {

    public static AttachmentFile getAttachment() {
        return new AttachmentFile();
    }
}
