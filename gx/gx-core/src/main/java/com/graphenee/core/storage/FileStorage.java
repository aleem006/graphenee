package com.graphenee.core.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.concurrent.Future;

public interface FileStorage {

	/**
	 * The default implementation of method returns "folder/fileName"
	 * 
	 * @param folder - must be a valid folder path where file needs to upload.
	 * @param filePath - file to upload.
	 * @return
	 */
	default String resourcePath(String folder, String filePath) {
		if (folder == null) {
			return filePath;
		}
		File file = new File(filePath);
		return folder + File.separator + file.getName();
	}

	default InputStream resolve(String resourcePath) throws ResolveFailedException {
		File file = new File(resourcePath);
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new ResolveFailedException("Failed to resolve resource " + resourcePath);
		}
	}

	default URI resolveToURI(String resourcePath) throws ResolveFailedException {
		try {
			File file = new File(resourcePath);
			return file.toURI();
		} catch (Exception e) {
			throw new ResolveFailedException("Failed to resolve resource " + resourcePath);
		}
	}

	default Future<FileMetaData> save(String folder, String fileToSave) throws SaveFailedException {
		File file = new File(fileToSave);
		if (file.isFile()) {
			try {
				FileInputStream inputStream = new FileInputStream(file);
				return save(folder, file.getName(), inputStream);
			} catch (IOException e) {
				throw new SaveFailedException(e);
			}
		}
		throw new SaveFailedException(fileToSave + " is not a file");
	}

	Future<FileMetaData> save(String folder, String fileName, InputStream inputStream) throws SaveFailedException;

	public static interface FileStorageDelegate {
		void onComplete(FileMetaData fileMetaData);
	}

	public static class FileMetaData {
		private String resourcePath;
		private int fileSize;
		private String checksum;
		private String fileName;

		public FileMetaData(String resourcePath, String fileName, int fileSize, String checksum) {
			this.resourcePath = resourcePath;
			this.fileName = fileName;
			this.fileSize = fileSize;
			this.checksum = checksum;
		}

		public String getResourcePath() {
			return resourcePath;
		}

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public int getFileSize() {
			return fileSize;
		}

		public void setFileSize(int fileSize) {
			this.fileSize = fileSize;
		}

		public String getChecksum() {
			return checksum;
		}

	}

}
