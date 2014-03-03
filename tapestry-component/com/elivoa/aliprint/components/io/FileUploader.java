package com.elivoa.aliprint.components.io;

import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.SupportsInformalParameters;

/**
 * File uploader
 * 
 * @author bogao [elivoa|gmail.com], Aug 3, 2012 At Tsinghua <BR>
 */
@SupportsInformalParameters
@Import(library = "ajaxupload.js", stylesheet = "ajaxupload.css")
public class FileUploader {

	/**
	 * folder on server to store uploaded file.
	 */
	@Parameter(required = true)
	private String folder;

	@Persist
	private String _folder;

	
	
}
