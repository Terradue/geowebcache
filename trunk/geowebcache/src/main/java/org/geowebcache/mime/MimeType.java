/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * @author Arne Kepp, The Open Planning Project, Copyright 2008
 *  
 */
package org.geowebcache.mime;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MimeType {
    protected String mimeType;
    
    protected String format;

    protected String fileExtension;

    protected String internalName;
    
    protected boolean supportsTiling;

    private static Log log = LogFactory
            .getLog(org.geowebcache.mime.MimeType.class);

    //public MimeType(boolean supportsTiling) {
    //    this.supportsTiling = supportsTiling;
    //}

    public MimeType(String mimeType, String fileExtension, String internalName, String format, boolean supportsTiling) {
        this.mimeType = mimeType;
        this.fileExtension = fileExtension;
        this.internalName = internalName;
        this.format = format;
        this.supportsTiling = supportsTiling;
    }
    
    //public MimeType(String mimeType, String fileExtension, String internalName, boolean supportsTiling) {
    //    this.mimeType = mimeType;
    //    this.fileExtension = fileExtension;
    //    this.internalName = internalName;
    //    this.supportsTiling = supportsTiling;
    //    this.format = null;
    //}

    // The string representing the MIME type
    public String getMimeType() {
        return mimeType;
    }
    
    /**
     * Returns the format string, which can be different from 
     * 
     * @return format or mimetype
     */
    public String getFormat() {
        if(format != null) {
            return format;
        }
        return mimeType;
    }

    // Used for saving to files etc
    public String getFileExtension() {
        return fileExtension;
    }

    // Used for internal purposes, like picking image renderer
    public String getInternalName() {
        return internalName;
    }
    
    public boolean supportsTiling() {
        return supportsTiling;
    }
    
    /**
     * Get the MIME type object for a given MIME type string
     * 
     * @param mimeStr
     * @return
     */
    public static MimeType createFromFormat(String formatStr) throws MimeException {
        MimeType mimeType = null;
        if(formatStr == null) {
            throw new MimeException("formatStr was not set");
        }
        
        if (formatStr.length() > 6 
                && formatStr.substring(0, 6).equalsIgnoreCase("image/")) {
            mimeType = ImageMime.checkForFormat(formatStr);
        }
        
        if(mimeType == null) {
            mimeType = XMLMime.checkForFormat(formatStr);
        }

        if (mimeType == null) {
            log.error("Unsupported format request: " + formatStr + ", returning null");
        }
        return mimeType;
    }


    

    /**
     * Get the MIME type object for a given file extension
     * 
     * @param mimeStr
     * @return
     */
    public static MimeType createFromExtension(String fileExtension) throws MimeException {
        MimeType mimeType = null;

        mimeType = ImageMime.checkForExtension(fileExtension);
        if (mimeType != null) {
            return mimeType;
        }

        mimeType = XMLMime.checkForExtension(fileExtension);
        if (mimeType != null) {
            return mimeType;
        }

        log.error("Unsupported MIME type: " + fileExtension
                + ", returning null");
        return null;
    }

    public boolean equals(Object obj) {
        if (obj.getClass() == this.getClass()) {
            MimeType mimeObj = (MimeType) obj;
            if (this.mimeType.equalsIgnoreCase(mimeObj.mimeType)) {
                return true;
            }
        }
        return false;
    }
}