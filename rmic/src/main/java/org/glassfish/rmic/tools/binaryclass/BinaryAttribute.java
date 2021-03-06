/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1994-2018 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package org.glassfish.rmic.tools.binaryclass;

import org.glassfish.rmic.tools.java.Constants;
import org.glassfish.rmic.tools.java.Environment;
import org.glassfish.rmic.tools.java.Identifier;

import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * This class is used to represent an attribute from a binary class.
 * This class should go away once arrays are objects.
 *
 * WARNING: The contents of this source file are not part of any
 * supported API.  Code that depends on them does so at its own risk:
 * they are subject to change or removal without notice.
 */
public final
class BinaryAttribute implements Constants {
    Identifier name;
    byte data[];
    BinaryAttribute next;

    /**
     * Constructor
     */
    BinaryAttribute(Identifier name, byte data[], BinaryAttribute next) {
        this.name = name;
        this.data = data;
        this.next = next;
    }

    /**
     * Load a list of attributes
     */
    public static BinaryAttribute load(DataInputStream in, BinaryConstantPool cpool, int mask) throws IOException {
        BinaryAttribute atts = null;
        int natt = in.readUnsignedShort();  // JVM 4.6 method_info.attrutes_count

        for (int i = 0 ; i < natt ; i++) {
            // id from JVM 4.7 attribute_info.attribute_name_index
            Identifier id = cpool.getIdentifier(in.readUnsignedShort());
            // id from JVM 4.7 attribute_info.attribute_length
            int len = in.readInt();

            if (id.equals(idCode) && ((mask & ATT_CODE) == 0)) {
                in.skipBytes(len);
            } else {
                byte data[] = new byte[len];
                in.readFully(data);
                atts = new BinaryAttribute(id, data, atts);
            }
        }
        return atts;
    }

    // write out the Binary attributes to the given stream
    // (note that attributes may be null)
    static void write(BinaryAttribute attributes, DataOutputStream out,
                      BinaryConstantPool cpool, Environment env) throws IOException {
        // count the number of attributes
        int attributeCount = 0;
        for (BinaryAttribute att = attributes; att != null; att = att.next)
            attributeCount++;
        out.writeShort(attributeCount);

        // write out each attribute
        for (BinaryAttribute att = attributes; att != null; att = att.next) {
            Identifier name = att.name;
            byte data[] = att.data;
            // write the identifier
            out.writeShort(cpool.indexString(name.toString(), env));
            // write the length
            out.writeInt(data.length);
            // write the data
            out.write(data, 0, data.length);
        }
    }

    /**
     * Accessors
     */

    public Identifier getName() { return name; }

    public byte getData()[] { return data; }

    public BinaryAttribute getNextAttribute() { return next; }

}
