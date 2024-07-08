package com.anytask.webserver.configuration

import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.annotation.Nulls
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@ComponentScan("com.anytask.*")
@EntityScan(basePackages = ["com.anytask.models.entity.*"])
@EnableJpaRepositories(basePackages = ["com.anytask.repository.*"])
open class ApplicationConfiguration : WebMvcConfigurer{

    @Bean
    open fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder(10)
    }

    @Bean
    open fun jacksonObjectMapper(builder: Jackson2ObjectMapperBuilder): ObjectMapper? {
        val objectMapper = builder.createXmlMapper(false).build<ObjectMapper>()
        objectMapper.configOverride(String::class.java).setterInfo = JsonSetter.Value.forValueNulls(Nulls.AS_EMPTY)
        return objectMapper
    }
}
