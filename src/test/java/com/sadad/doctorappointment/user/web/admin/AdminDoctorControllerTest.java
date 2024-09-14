package com.sadad.doctorappointment.user.web.admin;

import com.sadad.doctorappointment.ApplicationTests;
import com.sadad.doctorappointment.doctor.dto.DoctorDto;
import com.sadad.doctorappointment.user.dto.UserDto;
import com.sadad.doctorappointment.user.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.concurrent.CompletableFuture;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log4j2
public class AdminDoctorControllerTest extends ApplicationTests {

    @Autowired
    private UserService userService;
    private DoctorDto validDoctorDto;
    private DoctorDto validDoctorDtoForUpdate;
    private UserDto userDto;


    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        userDto = userService.saveOrUpdate(UserDto.builder()
                .firstName("firstNameUserTest")
                .lastName("lastNameUserTest")
                .email("email@test.com")
                .password("123")
                .username("test")
                .phoneNumber("09129231440")
                .enabled(true)
                .build());

        validDoctorDto = new DoctorDto();
        validDoctorDto.setUserId(userDto.getId());
        validDoctorDto.setSpecialization("جراح");
        validDoctorDto.setStartWorkTime("07:00");
        validDoctorDto.setEndWorkTime("17:00");

        validDoctorDtoForUpdate = new DoctorDto();
        validDoctorDtoForUpdate.setUserId(userDto.getId());
        validDoctorDtoForUpdate.setSpecialization("جراح داخلی");
        validDoctorDtoForUpdate.setStartWorkTime("07:00");
        validDoctorDtoForUpdate.setEndWorkTime("17:00");


    }

    @Test
    @Order(0)
    public void saveDoctor_Success() throws Exception {
        //mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(post("/admin/doctor/saveDoctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDoctorDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(userDto.getId()));

    }

    @Test
    public void UpdateDoctor_Success() throws Exception {
        mockMvc.perform(post("/admin/doctor/saveDoctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDoctorDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(userDto.getId()));


        mockMvc.perform(post("/admin/doctor/saveDoctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDoctorDtoForUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(userDto.getId()))
                .andExpect(jsonPath("$.specialization").value(validDoctorDtoForUpdate.getSpecialization()));
    }

    @Test
    public void UpdateDoctor_NotExistsUser() throws Exception {
        var dto = new DoctorDto();
        dto.setUserId(313L);
        dto.setSpecialization("جراح");
        dto.setStartWorkTime("07:00");
        dto.setEndWorkTime("17:00");

        mockMvc.perform(post("/admin/doctor/saveDoctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("user.not.found.exception"));
    }

    @Test
    public void saveDoctor_InvalidTimeRange() throws Exception {
        DoctorDto invalidDoctorDto = new DoctorDto();
        invalidDoctorDto.setUserId(1L);
        invalidDoctorDto.setSpecialization("جراح");
        invalidDoctorDto.setStartWorkTime("18:00");
        invalidDoctorDto.setEndWorkTime("08:00");
        mockMvc.perform(post("/admin/doctor/saveDoctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDoctorDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("doctor.FromTime.isAfter.ToTime"));


    }

    @Test
    public void saveDoctor_ValidationError() throws Exception {
        var dto = new DoctorDto();
        dto.setUserId(1L);
        dto.setSpecialization("جراح");
        dto.setStartWorkTime("1sd8:000");
        dto.setEndWorkTime(null);
        mockMvc.perform(post("/admin/doctor/saveDoctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.error").value("validation.method.argument.not.valid"));
    }


    @Test
    public void updateDoctor_ConcurrentRequests_RaceCondition() throws Exception {
        mockMvc.perform(post("/admin/doctor/saveDoctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDoctorDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(userDto.getId()));


        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            try {
                var updateDto = new DoctorDto();
                updateDto.setUserId(userDto.getId());
                updateDto.setSpecialization("جراح مغز");
                updateDto.setStartWorkTime("07:00");
                updateDto.setEndWorkTime("17:00");

                var res = mockMvc.perform(post("/admin/doctor/saveDoctor")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateDto)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.userId").value(userDto.getId()))
                        .andExpect(jsonPath("$.specialization").value(updateDto.getSpecialization()))
                        .andReturn()
                        .getResponse()
                        .getContentAsString();
                System.out.println("future1 runAsync ");
                log.info(res);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }, taskExecutor);

        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
            try {
                var updateDto = new DoctorDto();
                updateDto.setUserId(userDto.getId());
                updateDto.setSpecialization("جراح اطفال");
                updateDto.setStartWorkTime("07:00");
                updateDto.setEndWorkTime("17:00");
                var res = mockMvc.perform(post("/admin/doctor/saveDoctor")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateDto)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.userId").value(userDto.getId()))
                        .andExpect(jsonPath("$.specialization").value(updateDto.getSpecialization()))
                        .andReturn()
                        .getResponse()
                        .getContentAsString();
                System.out.println("future2 runAsync ");

                log.info(res);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, taskExecutor);

        CompletableFuture.allOf(future1, future2).join();

    }


}
