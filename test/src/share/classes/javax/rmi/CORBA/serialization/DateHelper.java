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
/* @(#)DateHelper.java	1.3 99/06/07 */
/*
 * Licensed Materials - Property of IBM
 * RMI-IIOP v1.0
 * Copyright IBM Corp. 1998 1999  All Rights Reserved
 *
 * US Government Users Restricted Rights - Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

package javax.rmi.CORBA.serialization;

/**
* DateHelper.java
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from java/util/Date.idl
* 01 June 1999 20:22:16 o'clock GMT+00:00
*/

abstract public class DateHelper
{
  private static String  _id = "RMI:java.util.Date:2B112DD1F3049E5C:686A81014B597419";

  public static void insert (org.omg.CORBA.Any a, Date that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static Date extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  private static boolean __active = false;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      synchronized (org.omg.CORBA.TypeCode.class)
      {
        if (__typeCode == null)
        {
          if (__active)
          {
            return org.omg.CORBA.ORB.init().create_recursive_tc ( _id );
          }
          __active = true;
          org.omg.CORBA.ValueMember[] _members0 = new org.omg.CORBA.ValueMember[0];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          __typeCode = org.omg.CORBA.ORB.init ().create_value_tc (_id, "Date", org.omg.CORBA.VM_CUSTOM.value, null, _members0);
          __active = false;
        }
      }
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static Date read (org.omg.CORBA.portable.InputStream istream)
  {
    return (Date)((org.omg.CORBA_2_3.portable.InputStream) istream).read_value (id ());
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, Date value)
  {
    ((org.omg.CORBA_2_3.portable.OutputStream) ostream).write_value (value, id ());
  }


  public static Date create__ (org.omg.CORBA.ORB _orb)
  {
    try {
      DateValueFactory _factory = (DateValueFactory)
          ((org.omg.CORBA_2_3.ORB) _orb).lookup_value_factory(id());
      return _factory.create__ ();
    } catch (ClassCastException _ex) {
      throw new org.omg.CORBA.BAD_PARAM ();
    }
  }

  public static Date create__long__long__long (org.omg.CORBA.ORB _orb, int arg0, int arg1, int arg2)
  {
    try {
      DateValueFactory _factory = (DateValueFactory)
          ((org.omg.CORBA_2_3.ORB) _orb).lookup_value_factory(id());
      return _factory.create__long__long__long (arg0, arg1, arg2);
    } catch (ClassCastException _ex) {
      throw new org.omg.CORBA.BAD_PARAM ();
    }
  }

  public static Date create__long__long__long__long__long (org.omg.CORBA.ORB _orb, int arg0, int arg1, int arg2, int arg3, int arg4)
  {
    try {
      DateValueFactory _factory = (DateValueFactory)
          ((org.omg.CORBA_2_3.ORB) _orb).lookup_value_factory(id());
      return _factory.create__long__long__long__long__long (arg0, arg1, arg2, arg3, arg4);
    } catch (ClassCastException _ex) {
      throw new org.omg.CORBA.BAD_PARAM ();
    }
  }

  public static Date create__long__long__long__long__long__long (org.omg.CORBA.ORB _orb, int arg0, int arg1, int arg2, int arg3, int arg4, int arg5)
  {
    try {
      DateValueFactory _factory = (DateValueFactory)
          ((org.omg.CORBA_2_3.ORB) _orb).lookup_value_factory(id());
      return _factory.create__long__long__long__long__long__long (arg0, arg1, arg2, arg3, arg4, arg5);
    } catch (ClassCastException _ex) {
      throw new org.omg.CORBA.BAD_PARAM ();
    }
  }

  public static Date create__long_long (org.omg.CORBA.ORB _orb, long arg0)
  {
    try {
      DateValueFactory _factory = (DateValueFactory)
          ((org.omg.CORBA_2_3.ORB) _orb).lookup_value_factory(id());
      return _factory.create__long_long (arg0);
    } catch (ClassCastException _ex) {
      throw new org.omg.CORBA.BAD_PARAM ();
    }
  }

  public static Date create__CORBA_WStringValue (org.omg.CORBA.ORB _orb, String arg0)
  {
    try {
      DateValueFactory _factory = (DateValueFactory)
          ((org.omg.CORBA_2_3.ORB) _orb).lookup_value_factory(id());
      return _factory.create__CORBA_WStringValue (arg0);
    } catch (ClassCastException _ex) {
      throw new org.omg.CORBA.BAD_PARAM ();
    }
  }

}
