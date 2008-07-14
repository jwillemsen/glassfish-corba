/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2003-2007 Sun Microsystems, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License. You can obtain
 * a copy of the License at https://glassfish.dev.java.net/public/CDDL+GPL.html
 * or glassfish/bootstrap/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/bootstrap/legal/LICENSE.txt.
 * Sun designates this particular file as subject to the "Classpath" exception
 * as provided by Sun in the GPL Version 2 section of the License file that
 * accompanied this code.  If applicable, add the following below the License
 * Header, with the fields enclosed by brackets [] replaced by your own
 * identifying information: "Portions Copyrighted [year]
 * [name of copyright owner]"
 *
 * Contributor(s):
 *
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

package com.sun.corba.se.impl.orbutil.threadpool;

import com.sun.corba.se.spi.orbutil.threadpool.NoSuchThreadPoolException;
import com.sun.corba.se.spi.orbutil.threadpool.ThreadPool;
import com.sun.corba.se.spi.orbutil.threadpool.ThreadPoolManager;
import com.sun.corba.se.spi.orbutil.threadpool.ThreadPoolChooser;

import com.sun.corba.se.impl.orbutil.threadpool.ThreadPoolImpl;
import com.sun.corba.se.spi.orbutil.ORBConstants;

public class ThreadPoolManagerImpl implements ThreadPoolManager 
{ 
    private ThreadPool threadPool ;

    public ThreadPoolManagerImpl( ThreadGroup tg )
    {
	// Use unbounded threadpool in J2SE ORB
	// ThreadPoolManager from s1as appserver code base can be set in the
	// ORB. ThreadPools in the appserver are bounded. In that situation 
	// the ThreadPool in this ThreadPoolManager will have its threads 
	// die after the idle timeout.
	// XXX Should there be cleanup when ORB.shutdown is called if the
	// ORB owns the ThreadPool?
	threadPool = new ThreadPoolImpl( tg,
	    ORBConstants.THREADPOOL_DEFAULT_NAME ) ;
    }

    /** 
    * This method will return an instance of the threadpool given a threadpoolId, 
    * that can be used by any component in the app. server. 
    *
    * @throws NoSuchThreadPoolException thrown when invalid threadpoolId is passed
    * as a parameter
    */ 
    public ThreadPool getThreadPool(String threadpoolId) 
        throws NoSuchThreadPoolException {
            
        return threadPool;
    }

    /** 
    * This method will return an instance of the threadpool given a numeric threadpoolId. 
    * This method will be used by the ORB to support the functionality of 
    * dedicated threadpool for EJB beans 
    *
    * @throws NoSuchThreadPoolException thrown when invalidnumericIdForThreadpool is passed
    * as a parameter
    */ 
    public ThreadPool getThreadPool(int numericIdForThreadpool) 
        throws NoSuchThreadPoolException { 

        return threadPool;
    }

    /** 
    * This method is used to return the numeric id of the threadpool, given a String 
    * threadpoolId. This is used by the POA interceptors to add the numeric threadpool 
    * Id, as a tagged component in the IOR. This is used to provide the functionality of 
    * dedicated threadpool for EJB beans 
    */ 
    public int  getThreadPoolNumericId(String threadpoolId) { 
        return 0;
    }

    /** 
    * Return a String Id for a numericId of a threadpool managed by the threadpool 
    * manager 
    */ 
    public String getThreadPoolStringId(int numericIdForThreadpool) {
       return "";
    } 

    /** 
    * Returns the first instance of ThreadPool in the ThreadPoolManager 
    */ 
    public ThreadPool getDefaultThreadPool() {
        return threadPool;
    }

    /**
     * Return an instance of ThreadPoolChooser based on the componentId that was
     * passed as argument
     */
    public ThreadPoolChooser getThreadPoolChooser(String componentId) {
	//FIXME: This method is not used, but should be fixed once
	//nio select starts working and we start using ThreadPoolChooser
	return null;
    }
    /**
     * Return an instance of ThreadPoolChooser based on the componentIndex that was
     * passed as argument. This is added for improved performance so that the caller
     * does not have to pay the cost of computing hashcode for the componentId
     */
    public ThreadPoolChooser getThreadPoolChooser(int componentIndex) {
	//FIXME: This method is not used, but should be fixed once
	//nio select starts working and we start using ThreadPoolChooser
	return null;
    }

    /**
     * Sets a ThreadPoolChooser for a particular componentId in the ThreadPoolManager. This 
     * would enable any component to add a ThreadPoolChooser for their specific use
     */
    public void setThreadPoolChooser(String componentId, ThreadPoolChooser aThreadPoolChooser) {
	//FIXME: This method is not used, but should be fixed once
	//nio select starts working and we start using ThreadPoolChooser
    }

    /**
     * Gets the numeric index associated with the componentId specified for a 
     * ThreadPoolChooser. This method would help the component call the more
     * efficient implementation i.e. getThreadPoolChooser(int componentIndex)
     */
    public int getThreadPoolChooserNumericId(String componentId) {
	//FIXME: This method is not used, but should be fixed once
	//nio select starts working and we start using ThreadPoolChooser
	return 0;
    }

} 

// End of file.
