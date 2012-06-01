package com.sun.corba.ee.impl.encoding;

import org.omg.CORBA.portable.InputStream;

import java.io.Serializable;

public class IDLValueHelper {
    public static Serializable read(InputStream is) {
        byte b = is.read_octet();
        return new IDLValue(b);
    }

}
