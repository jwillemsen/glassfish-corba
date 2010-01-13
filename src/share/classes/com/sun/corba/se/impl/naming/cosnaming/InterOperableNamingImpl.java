/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2002-2010 Sun Microsystems, Inc. All rights reserved.
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

package com.sun.corba.se.impl.naming.cosnaming;

import java.io.StringWriter;

import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExtPackage.InvalidAddress;


/**
 * Class InteroperableNamingImpl implements the methods defined
 * for NamingContextExt which is part of Interoperable Naming
 * Service specifications. This class is added for doing more
 * of Parsing and Building of Stringified names according to INS
 * Spec.
 */
public class InterOperableNamingImpl
{
   /**
     * Method which stringifies the Name Components given as the input 
     * parameter.
     *
     * @param theNameComponents Array of Name Components (Simple or Compound
     * Names)
     * @return string which is the stringified reference.
     */
    public String convertToString( org.omg.CosNaming.NameComponent[] 
                                   theNameComponents )
    {
        boolean first = true ;
        final StringBuffer sb = new StringBuffer() ;
        for (NameComponent nc : theNameComponents) {
            final String temp = convertNameComponentToString( nc ) ;
            sb.append( temp ) ;
            if (first) {
                first = false ;
            } else {
                sb.append( '/' ) ;
            }
        }

        return sb.toString() ;
    }

    private boolean isEmpty( String str ) {
        return str==null || str.length() == 0 ;
    }

    private boolean contains( String str, char ch ) {
        return str.indexOf( ch ) != -1 ;
    }

   /** This method converts a single Namecomponent to String, By adding Escapes
    *  If neccessary.
    */
    private String convertNameComponentToString( 
        org.omg.CosNaming.NameComponent theNameComponent ) 
    {
        final String id = addEscape( theNameComponent.id ) ;
        final String kind = addEscape( theNameComponent.kind ) ;
        final StringBuffer sb = new StringBuffer() ;

        if (!isEmpty(id)) {
            sb.append( id ) ;
        }

        sb.append( '.' ) ;

        if (!isEmpty(kind)) {
            sb.append(kind) ;
        }

        return sb.toString() ;
    }


   /** This method adds escape '\' for the Namecomponent if neccessary
    */
   private String addEscape( String value )
   {
        if ((value != null) && (contains( value, '.' ) ||
            contains( value, '/' ))) {
            final StringBuffer theNewValue = new StringBuffer() ;
            for( int i = 0; i < value.length( ); i++ ) {
                char c = value.charAt( i );
                if ((c == '.') || (c == '/')) {
                    theNewValue.append( '\\' );
                }

                // Adding escape for the "."
                theNewValue.append( c );
            }

            return theNewValue.toString() ;
        } else {
            return value;
        }
   } 

   /**
     * Method which converts the Stringified name into Array of Name Components.
     *
     * @param theStringifiedName which is the stringified name.
    * @return  Array of Name Components (Simple or Compound Names)
    * @throws org.omg.CosNaming.NamingContextPackage.InvalidName
     */
   public org.omg.CosNaming.NameComponent[] convertToNameComponent( 
       String theStringifiedName )
       throws org.omg.CosNaming.NamingContextPackage.InvalidName
   {
	String[] components = breakStringToNameComponents( theStringifiedName );
        if (( components == null ) || (components.length == 0)) {
            return null;
        } 

        NameComponent[] theNameComponents = new NameComponent[components.length];
        for( int i = 0; i < components.length; i++ ) {
            theNameComponents[i] = createNameComponentFromString( 
                components[i] );
        }

        return theNameComponents;
   }

   /** Step1 in converting Stringified name into  array of Name Component 
     * is breaking the String into multiple name components
     */
   private String[] breakStringToNameComponents( final String sname ) {
       int[] theIndices = new int[100];
       int theIndicesIndex = 0;

       for(int index = 0; index <= sname.length(); ) {
           theIndices[theIndicesIndex] = sname.indexOf( '/',
                index );
           if( theIndices[theIndicesIndex] == -1 ) {
               // This is the end of all the occurence of '/' and hence come
               // out of the loop
               index = sname.length()+1;
           }
           else {
               // If the '/' is found, first check whether it is 
               // preceded by escape '\'
               // If not then set theIndices and increment theIndicesIndex 
               // and also set the index else just ignore the '/'
               if( (theIndices[theIndicesIndex] > 0 )
               && (sname.charAt(
                   theIndices[theIndicesIndex]-1) == '\\') )
               {
                  index = theIndices[theIndicesIndex] + 1;
                  theIndices[theIndicesIndex] = -1;
               }
               else {
                  index = theIndices[theIndicesIndex] + 1;
                  theIndicesIndex++;
               }
           }
        }
        if( theIndicesIndex == 0 ) {
            String[] tempString = new String[1];
            tempString[0] = sname;
            return tempString;
        }
        if( theIndicesIndex != 0 ) {
            theIndicesIndex++;
        }
        return StringComponentsFromIndices( theIndices, theIndicesIndex, 
                                            sname );
    } 

   /** This method breaks one big String into multiple substrings based
     * on the array of index passed in.
     */ 
   private String[] StringComponentsFromIndices( int[] theIndices, 
          int indicesCount, String theStringifiedName )
   {
       String[] theStringComponents = new String[indicesCount];
       int firstIndex = 0;
       int lastIndex = theIndices[0];
       for( int i = 0; i < indicesCount; i++ ) {
           theStringComponents[i] = theStringifiedName.substring( firstIndex, 
             lastIndex );
           if( ( theIndices[i] < theStringifiedName.length() - 1 ) 
             &&( theIndices[i] != -1 ) ) 
           {
               firstIndex = theIndices[i]+1;
           }
           else {
               firstIndex = 0;
               i = indicesCount;
           }
           if( (i+1 < theIndices.length) 
            && (theIndices[i+1] < (theStringifiedName.length() - 1)) 
            && (theIndices[i+1] != -1) )
           {
               lastIndex = theIndices[i+1];
           }
           else {
               i = indicesCount;
           }
           // This is done for the last component
           if( firstIndex != 0 && i == indicesCount ) {
               theStringComponents[indicesCount-1] = 
               theStringifiedName.substring( firstIndex );
           }
       }
       return theStringComponents;
   }

   /** Step 2: After Breaking the Stringified name into set of NameComponent
     * Strings, The next step is to create Namecomponents from the substring
     * by removing the escapes if there are any.
     */ 
   private NameComponent createNameComponentFromString( 
    	String theStringifiedNameComponent )
        throws org.omg.CosNaming.NamingContextPackage.InvalidName

   {
        String id = null;
        String kind = null;
        if( ( theStringifiedNameComponent == null ) 
         || ( theStringifiedNameComponent.length( ) == 0) 
         || ( theStringifiedNameComponent.endsWith(".") ) )
        {
            // If any of the above is true, then we create an invalid Name
            // Component to indicate that it is an invalid name.
            throw new org.omg.CosNaming.NamingContextPackage.InvalidName( );
        }

        int index = theStringifiedNameComponent.indexOf( '.', 0 );
        // The format could be XYZ (Without kind)
        if( index == -1 ) {
            id = theStringifiedNameComponent;
        }
        // The format is .XYZ (Without ID)
        else if( index == 0 ) {
            // This check is for the Namecomponent which is just "." meaning Id 
            // and Kinds are null
            if( theStringifiedNameComponent.length( ) != 1 ) {
                kind = theStringifiedNameComponent.substring(1);
            }
        }
        else
        {
            if( theStringifiedNameComponent.charAt(index-1) != '\\' ) {
                id = theStringifiedNameComponent.substring( 0, index);
                kind = theStringifiedNameComponent.substring( index + 1 );
            }
            else {
                boolean kindfound = false;
                while( (index < theStringifiedNameComponent.length() )
                     &&( kindfound != true ) )
                {
                    index = theStringifiedNameComponent.indexOf( '.',index + 1);
                    if( index > 0 ) {
                        if( theStringifiedNameComponent.charAt( 
				index - 1 ) != '\\' )
                        {
                            kindfound = true;
                        }
                    }
                    else
                    {
                        // No more '.', which means there is no Kind
                        index = theStringifiedNameComponent.length();
                    }
                }
                if( kindfound == true ) {
                    id = theStringifiedNameComponent.substring( 0, index);
                    kind = theStringifiedNameComponent.substring(index + 1 ); 
                }
                else {
                    id = theStringifiedNameComponent;
                }
            }
        }
        id = cleanEscapeCharacter( id );
        kind = cleanEscapeCharacter( kind );
	if( id == null ) {
		id = "";
	}
	if( kind == null ) {
		kind = "";
	}
        return new NameComponent( id, kind );
   }

  
   /** This method cleans the escapes in the Stringified name and returns the 
     * correct String
     */
   private String cleanEscapeCharacter( String theString )
   {
        if( ( theString == null ) || (theString.length() == 0 ) ) {
                return theString;
        }
        int index = theString.indexOf( '\\' );
        if( index == 0 ) {
            return theString;
        }
        else {
            StringBuffer src = new StringBuffer( theString );
            StringBuffer dest = new StringBuffer( );
            char c;
            for( int i = 0; i < theString.length( ); i++ ) {
                c = src.charAt( i );
                if( c != '\\' ) {
                    dest.append( c );
                } else {
                    if( i+1 < theString.length() ) {
                        char d = src.charAt( i + 1 );
                        // If there is a AlphaNumeric character after a \
                        // then include slash, as it is not intended as an
                        // escape character.
                        if( Character.isLetterOrDigit(d) ) {
                            dest.append( c );
                        }
                    }
                }
            }
            return new String(dest);
        }
   } 

   /**
     * Method which converts the Stringified name  and Host Name Address into
     * a URL based Name
     *
     * @param address which is ip based host name
     * @param name which is the stringified name.
     * @return  url based Name.
     */
    public String createURLBasedAddress( String address, String name )
        throws InvalidAddress
    {
	String theurl = null;
        if( ( address == null )
          ||( address.length() == 0 ) ) {
            throw new InvalidAddress();
        }
        else {
            theurl = "corbaname:" + address + "#" + encode( name );
        }
        return theurl;
    }

    /** Encodes the string according to RFC 2396 IETF spec required by INS.
     */
    private String encode( String stringToEncode ) {
        StringWriter theStringAfterEscape = new StringWriter();
        int byteCount = 0;
        for( int i = 0; i < stringToEncode.length(); i++ )
        {
            char c = stringToEncode.charAt( i ) ;
            if( Character.isLetterOrDigit( c ) ) {
                theStringAfterEscape.write( c );
            }
            // Do no Escape for characters in this list
            // RFC 2396
            else if((c == ';') || (c == '/') || (c == '?')
            || (c == ':') || (c == '@') || (c == '&') || (c == '=')
            || (c == '+') || (c == '$') || (c == ';') || (c == '-')
            || (c == '_') || (c == '.') || (c == '!') || (c == '~')
            || (c == '*') || (c == ' ') || (c == '(') || (c == ')') )
            {
                theStringAfterEscape.write( c );
            }
            else {
                // Add escape
                theStringAfterEscape.write( '%' );
                String hexString = Integer.toHexString( (int) c );  
                theStringAfterEscape.write( hexString ); 
            }
        }
        return theStringAfterEscape.toString();
    }

}

