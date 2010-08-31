/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 1997-2010 Oracle and/or its affiliates. All rights reserved.
 * 
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/bootstrap/legal/LICENSE.txt.
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

package org.omg.CORBA;

/**
 * The representation of a <code>DynAny</code> object that is associated
 *  with an IDL value type.
 * @deprecated Use the new <a href="../DynamicAny/DynValue.html">DynValue</a> instead
 */
public interface DynValue extends org.omg.CORBA.Object, org.omg.CORBA.DynAny {

    /**
     * Returns the name of the current member while traversing a
     * <code>DynAny</code> object that represents a Value object.
     *
     * @return the name of the current member
     */
    String current_member_name();

    /**
     * Returns the <code>TCKind</code> object that describes the current member.
     *
     * @return the <code>TCKind</code> object corresponding to the current
     * member
     */
    TCKind current_member_kind();

    /**
     * Returns an array containing all the members of the value object
     * stored in this <code>DynValue</code>.
     *
     * @return an array of name-value pairs.
	 * @see #set_members
     */
    org.omg.CORBA.NameValuePair[] get_members();

    /**
     * Sets the members of the value object this <code>DynValue</code>
     * object represents to the given array of <code>NameValuePair</code>
	 * objects.
     *
     * @param value the array of name-value pairs to be set
     * @throws org.omg.CORBA.DynAnyPackage.InvalidSeq
     *         if an inconsistent value is part of the given array
	 * @see #get_members
     */
    void set_members(NameValuePair[] value)
	throws org.omg.CORBA.DynAnyPackage.InvalidSeq;
}
