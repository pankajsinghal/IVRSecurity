/**
 * 
 */
package com.bng.core.service;

import com.bng.core.bean.FileMeta;
import com.bng.core.entity.Users;

/**
 * @author Harish Shan
 *
 */
public interface FileUpload 
{
	public void saveContent(FileMeta filemeta,Users currentUser);
	public Users getUser(String username);
	public void uploadFiles(String filename);
}
