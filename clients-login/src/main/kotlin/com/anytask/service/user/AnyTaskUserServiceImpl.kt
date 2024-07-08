package com.anytask.service.user

import com.anytask.models.entity.user.AnyTaskUser
import com.anytask.models.entity.user.Status
import com.anytask.repository.user.AnyTaskUserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

import java.util.*
import javax.imageio.IIOImage

import javax.imageio.ImageWriteParam

import javax.imageio.ImageIO

import javax.imageio.stream.ImageOutputStream

import javax.imageio.ImageWriter

import java.awt.image.BufferedImage
import java.io.*
import java.io.FileOutputStream

import java.io.IOException





@Service
@Transactional
open class AnyTaskUserServiceImpl(
    @Autowired
    private val anyTaskUserRepository: AnyTaskUserRepository
): AnyTaskUserService{

    override fun saveUser(user: AnyTaskUser): AnyTaskUser {
        return anyTaskUserRepository.save(user)
    }

    override fun getAllUsers(): List<AnyTaskUser> {
        return anyTaskUserRepository.findAll()
    }

    override fun getUserByEmail(email: String): AnyTaskUser {
        val user = anyTaskUserRepository.getByEmail(email = email)
        if (user != null) {
            return if (user.status == Status.ACTIVE) user else throw UnsupportedOperationException("User is not active and user status is ${user.status}.")
        } else {
            throw UnsupportedOperationException("User is not found.")
        }
    }

    override fun checkIfMailExistsActive(email: String): Boolean {
        return anyTaskUserRepository.getByEmail(email = email) != null
    }

    override fun upload(multipartFile: MultipartFile): Any? {
        return try {
            compressImage(multipartFile)
        } catch (e: Exception) {
            e.printStackTrace()
            e.message
        }
    }
}

@Throws(IOException::class)
fun compressImage(image: MultipartFile): ByteArray? {
    val inputStream: InputStream = image.inputStream
    val outputStream = ByteArrayOutputStream()
    val imageQuality = 0.2f

    // Create the buffered image
    val bufferedImage: BufferedImage = ImageIO.read(inputStream)

    // Get image writers
    val imageWriters = ImageIO.getImageWritersByFormatName("jpg") // Input your Format Name here
    if (!imageWriters.hasNext()) throw IllegalStateException("Writers Not Found!!")
    val imageWriter = imageWriters.next()
    val imageOutputStream = ImageIO.createImageOutputStream(outputStream)
    imageWriter.output = imageOutputStream
    val imageWriteParam = imageWriter.defaultWriteParam

    // Set the compress quality metrics
    imageWriteParam.compressionMode = ImageWriteParam.MODE_EXPLICIT
    imageWriteParam.compressionQuality = imageQuality

    // Compress and insert the image into the byte array.
    imageWriter.write(null, IIOImage(bufferedImage, null, null), imageWriteParam)
    val imageBytes = outputStream.toByteArray()
    writeBytesToFile("D:\\AnyTask\\clients-login\\src\\main\\resources\\"+image.originalFilename, imageBytes)
    // close all streams
    inputStream.close()
    outputStream.close()
    imageOutputStream.close()
    imageWriter.dispose()
    return imageBytes
}

//JDK 7 - FileOutputStream + try-with-resources
@Throws(IOException::class)
private fun writeBytesToFile(fileOutput: String, bytes: ByteArray) {
    FileOutputStream(fileOutput).use { fos -> fos.write(bytes) }
}