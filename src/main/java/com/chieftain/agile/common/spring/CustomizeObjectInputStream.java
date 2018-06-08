package com.chieftain.agile.common.spring;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

import org.springframework.context.annotation.Configuration;

/**
 * com.chieftain.agile.common.spring [workset_idea_01]
 * Created by Richard on 2018/6/5
 *
 * @author Richard on 2018/6/5
 */
@Configuration
public class CustomizeObjectInputStream extends ObjectInputStream {

    public CustomizeObjectInputStream(InputStream in) throws IOException {
        super(in);
    }

    public CustomizeObjectInputStream() throws IOException, SecurityException {
    }

    @Override
    protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
        String name = desc.getName();
        return Class.forName(name, false, Thread.currentThread().getContextClassLoader());
    }
}
