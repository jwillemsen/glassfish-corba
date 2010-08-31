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

package com.sun.corba.se.spi.presentation.rmi ;

import java.rmi.RemoteException ;

import org.omg.CORBA.portable.Delegate ;
import org.omg.CORBA.ORB ;
import org.omg.CORBA.Request ;
import org.omg.CORBA.Context ;
import org.omg.CORBA.NamedValue ;
import org.omg.CORBA.NVList ;
import org.omg.CORBA.ContextList ;
import org.omg.CORBA.ExceptionList ;
import org.omg.CORBA.Policy ;
import org.omg.CORBA.DomainManager ;
import org.omg.CORBA.SetOverrideType ;

import org.omg.CORBA.portable.OutputStream ;

/** Wrapper that can take any stub (object x such that StubAdapter.isStub(x))
 * and treat it as a DynamicStub.
 */
public class StubWrapper implements DynamicStub
{
    private org.omg.CORBA.Object object ;

    public StubWrapper( org.omg.CORBA.Object object ) 
    {
	if (!(StubAdapter.isStub(object)))
	    throw new IllegalStateException() ;

	this.object = object ;
    }

    public void setDelegate( Delegate delegate ) 
    {
	StubAdapter.setDelegate( object, delegate ) ;
    }

    public Delegate getDelegate() 
    {
	return StubAdapter.getDelegate( object ) ;
    }

    public ORB getORB() 
    {
	return StubAdapter.getORB( object ) ;
    }

    public String[] getTypeIds() 
    {
	return StubAdapter.getTypeIds( object ) ;
    }

    public void connect( ORB orb ) throws RemoteException 
    {
	StubAdapter.connect( object, (com.sun.corba.se.spi.orb.ORB)orb ) ;
    }

    public boolean isLocal() 
    {
	return StubAdapter.isLocal( object ) ;
    }

    public OutputStream request( String operation, boolean responseExpected ) 
    {
	return StubAdapter.request( object, operation, responseExpected ) ;
    }

    public boolean _is_a(String repositoryIdentifier)
    {
	return object._is_a( repositoryIdentifier ) ;
    }

    public boolean _is_equivalent(org.omg.CORBA.Object other)
    {
	return object._is_equivalent( other ) ;
    }

    public boolean _non_existent()
    {
	return object._non_existent() ;
    }

    public int _hash(int maximum)
    {
	return object._hash( maximum ) ;
    }

    public org.omg.CORBA.Object _duplicate()
    {
	return object._duplicate() ;
    }

    public void _release()
    {
	object._release() ;
    }

    public org.omg.CORBA.Object _get_interface_def()
    {
	return object._get_interface_def() ;
    }

    public Request _request(String operation)
    {
	return object._request( operation ) ;
    }

    public Request _create_request( Context ctx, String operation, NVList arg_list, 
	NamedValue result)
    {
	return object._create_request( ctx, operation, arg_list, result ) ;
    }

    public Request _create_request( Context ctx, String operation, NVList arg_list, 
	NamedValue result, ExceptionList exclist, ContextList ctxlist)
    {
	return object._create_request( ctx, operation, arg_list, result,
	    exclist, ctxlist ) ;
    }

    public Policy _get_policy(int policy_type)
    {
	return object._get_policy( policy_type ) ;
    }

    public DomainManager[] _get_domain_managers()
    {
	return object._get_domain_managers() ;
    }

    public org.omg.CORBA.Object _set_policy_override( Policy[] policies, 
	SetOverrideType set_add)
    {
	return object._set_policy_override( policies, set_add ) ;
    }
}
