package com.mfb.adm.api.rest.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.mfb.adm.comm.utils.Funciones;
import com.mfb.adm.api.rest.exceptions.FileStorageException;

@Service
public class FileStorageService {

	private final Path fileStorageLocation;
	
	@Value("${ruta.logo}")
	private String URL_LOGOS;

	@Autowired
	public FileStorageService(FileStoragePojo fileStoragePojo) {
		this.fileStorageLocation = Paths.get(fileStoragePojo.getUploadDir()).toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			throw new FileStorageException("No se puede crear el directorio donde se guardaran las imagenes.", ex);
		}
	}

	public String storeLogo(Long nit, MultipartFile file) {
		// Normalize file name
		String fileName = nit.toString()+".png";
		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new FileStorageException(
						"Lo sentimos! el nombre del archivo contiene una ruta invalida " + fileName);
			}
			Path targetLocation = (Path)Paths.get(URL_LOGOS+fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			return fileName;
		} catch (IOException ex) {
			throw new FileStorageException(
					"No se puede guardar el archivo " + fileName + ". Por favor intente de nuevo!", ex);
		}
	}
	
	public String storeFile(String prefijo, MultipartFile file) {
		// Normalize file name
		String ext = Funciones.obtenerExtension(StringUtils.cleanPath(file.getOriginalFilename()));
		String fileName = prefijo + "_" + Funciones.generarCodigo(15) + "." + ext;

		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new FileStorageException(
						"Lo sentimos! el nombre del archivo contiene una ruta invalida " + fileName);
			}

			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			return fileName;
		} catch (IOException ex) {
			throw new FileStorageException(
					"No se puede guardar el archivo " + fileName + ". Por favor intente de nuevo!", ex);
		}
	}

	public Resource loadFileAsResource(String fileName) {
		try {
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new MentionedFileNotFoundException("Archivo no encontrado " + fileName);
			}
		} catch (MalformedURLException ex) {
			throw new MentionedFileNotFoundException("Archivo no encontrado " + fileName, ex);
		}
	}

}
