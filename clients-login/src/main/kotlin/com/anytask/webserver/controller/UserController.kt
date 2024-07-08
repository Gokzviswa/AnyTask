package com.anytask.webserver.controller

import com.anytask.exceptions.UserAlreadyExistsException
import com.anytask.models.dto.user.AnyTaskUserDto
import com.anytask.models.dto.user.Login
import com.anytask.models.entity.user.AnyTaskUser
import com.anytask.models.mapper.user.UserMapper
import com.anytask.models.response.Response
import com.anytask.service.credentials.CredentialsService
import com.anytask.service.leadsOnline.LeadsOnlineService
import com.anytask.service.user.AnyTaskUserService
import com.twilio.rest.api.v2010.account.Message
import com.twilio.type.PhoneNumber
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import javassist.NotFoundException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*
import javax.imageio.IIOImage
import javax.imageio.ImageIO
import javax.imageio.ImageWriteParam
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/api_v1/user")
class UserController(
    @Autowired
    private val anyTaskUserService: AnyTaskUserService,
    @Autowired
    private val credentialsService: CredentialsService,
    @Autowired
    private val leadsOnlineService: LeadsOnlineService,
    private val userDataMapper: UserMapper
) {

    companion object {
        private val logger = LoggerFactory.getLogger(RestController::class.java)
    }

    @ApiOperation(
        value = "Health check endpoint",
        response = String::class
    )
    @GetMapping(value = ["/healthCheck"], produces = ["application/json"])
    fun healthCheck(): ResponseEntity<String> {
        return try {
            ResponseEntity.ok()
                .body("Any task spring boot is connected successfully.")
        } catch (ex: Throwable) {
            throw IllegalArgumentException(ex.message)
        }
    }

    @ApiOperation(
        value = "Returns all the onboarded users in the application.",
        response = AnyTaskUser::class
    )
    @ApiResponses(
        ApiResponse(code = 200, message = "")
    )
    @GetMapping(value = ["/all"], produces = ["application/json"])
    fun getAllUsers(): ResponseEntity<Response> {
        return try {
            ResponseEntity.ok().body(
                Response(
                    message = "Users are retrieved successfully",
                    data = anyTaskUserService.getAllUsers()
                )
            )
        } catch (ex: Throwable) {
            throw IllegalArgumentException(ex.message)
        }
    }

    @ApiOperation(
        value = "User signup",
        response = AnyTaskUser::class
    )
    @PostMapping("/signup")
    fun signUp(
        @RequestBody userData: AnyTaskUserDto
    ): ResponseEntity<Response> {
        validateSignUpData(userData)
        userData.validateSignupAndCreateId()
        return try {
            val anyTaskUser = anyTaskUserService.saveUser(userDataMapper.mapToAnyTask(userData))
            if (anyTaskUserService.checkIfMailExistsActive(userData.email)) {
                credentialsService.saveCredentials(userData, anyTaskUser.id)
            }
            ResponseEntity.ok().body(
                Response(
                    message = "User is created successfully",
                    data = anyTaskUserService.getUserByEmail(userData.email)
                )
            )
        } catch (ex: Throwable) {
            throw IllegalArgumentException(ex.message)
        }
    }

    @PostMapping(value = ["/upload/{directoryName}"], consumes = ["multipart/form-data"])
    fun upload(@RequestParam("file") file: MultipartFile, @PathVariable directoryName: String): Any? {
        logger.info("HIT -/upload | File Name : {}", file.originalFilename)
        val inputStream: InputStream = file.inputStream
        val outputStream = ByteArrayOutputStream()
        val imageQuality = 0.1f

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
        // close all streams
        inputStream.close()
        outputStream.close()
        imageOutputStream.close()
        imageWriter.dispose()
        println("Original: " + file.bytes.size + " bytes")
        println("Compressed: ${imageBytes.size} bytes")
        println(imageBytes.toString())
        return imageBytes
    }

    @PostMapping(value = ["/login"], produces = ["application/json"])
    fun login(
        @RequestBody login: Login,
        httpServletRequest: HttpServletRequest
    ): ResponseEntity<Response> {
        return try {
            val loggedIn = credentialsService.checkIfPasswordExists(login.userName, login.password)
            val a4 = leadsOnlineService.geHelloAge(anyTaskUserService.getUserByEmail(login.userName), login.pawnNo, login.imgURL)
            println("Message Sent successfully ${a4.toString()}")
            ResponseEntity.ok().body(
                Response(
                    message = "Login successful",
                    data = login
                )
            )
        } catch (e: UnsupportedOperationException) {
            ResponseEntity.badRequest().body(
                Response(
                    message = "Login failed.",
                    data = e.message
                )
            )
        }
    }

    @PostMapping(value = ["/upload"], consumes = ["multipart/form-data"])
    fun upload(@RequestParam("file") file: MultipartFile): Any? {
        return anyTaskUserService.upload(file)
    }

    private fun validateSignUpData(user: AnyTaskUserDto) {
        anyTaskUserService.checkIfMailExistsActive(user.email).let { if (it) throw UserAlreadyExistsException() }
    }

    private fun sendMessage(
        phoneNumber: String,
        customerName: String,
        pawnNo: Int,
        interestAmount: Double,
        interestRate: Double,
        principalAmount: Double,
        dueDate: Instant,
        messageType: String
    ): String {
        val dateTimeFormatter: SimpleDateFormat = SimpleDateFormat("MM-dd-yyyy")
        val editedDate = dateTimeFormatter.format(Date.from(dueDate))
        val messageContent = when (messageType) {
            "INTEREST_REMINDER" -> "Hi $customerName, \nThis is a PROPAWN payment reminder for interest that is due.\nPawn No - $pawnNo \nInterest Amount - $interestAmount \nDue Date - $editedDate \n\nPlease contact us at 786-960-1320 for more details."
            "DEFAULT_REMINDER" -> "Hi $customerName, \nThis is a PROPAWN default alert for the overdue pawn. Please make a payment to avoid default.\nPawn No - $pawnNo\nPrincipal Amount - $principalAmount \nPending Interest Amount - $interestAmount\n\nPlease contact us at 786-960-1320."
            "DEFAULT_FINAL_REMINDER" -> "Hi $customerName, \nThis is a PROPAWN final default alert for the overdue pawn. Please make a payment within 24 hours to avoid default.\nPawn No - $pawnNo \nPrincipal Amount - $principalAmount \nPending Interest Amount - $interestAmount\n\nPlease contact us at 786-960-1320."
            "WELCOME_PAWN_ONBOARD" -> "Hi $customerName, \nWelcome to PROPAWN, your pawn has been approved. Interest payments are due every 30 days.\nPawn No - $pawnNo \nPrincipal Amount - $principalAmount \nInterest Rate - $interestRate \nInterest Amount - $interestAmount\n\nPlease contact us at 786-960-1320. Thank you for your business."
            "PENDING_PAWN" -> "Hi $customerName, \nThere is a pawn pending to take action. Please have a look.\nPawn No - $pawnNo \nPrincipal Amount - $principalAmount \nInterest Rate - $interestRate \nInterest Amount - $interestAmount ."
            else -> throw NotFoundException("No text notification message type found.")
        }
        println(editedDate)
        println(messageContent)
        // Send a text message
        val message: Message = Message.creator(
            PhoneNumber(phoneNumber),
            "MG065a478166b10ff7b100e9a089efe3f9",
            messageContent
        ).create()
        return (phoneNumber to message.status).toString()
    }
}