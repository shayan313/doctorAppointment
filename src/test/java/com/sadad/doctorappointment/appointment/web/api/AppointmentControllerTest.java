package com.sadad.doctorappointment.appointment.web.api;

import com.sadad.doctorappointment.ApplicationTests;
import com.sadad.doctorappointment.appointment.dto.AppointmentRequest;
import com.sadad.doctorappointment.appointment.dto.SlotsRequest;
import com.sadad.doctorappointment.appointment.model.Appointment;
import com.sadad.doctorappointment.appointment.service.IAppointmentService;
import com.sadad.doctorappointment.doctor.dto.DoctorDto;
import com.sadad.doctorappointment.user.dto.UserDto;
import com.sadad.doctorappointment.user.service.IDoctorService;
import com.sadad.doctorappointment.user.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log4j2
class AppointmentControllerTest extends ApplicationTests {

    @Autowired
    IDoctorService doctorService;

    @Autowired
    UserService userService;

    @Autowired
    IAppointmentService appointmentService;

    private DoctorDto doctorDto;
    private SlotsRequest slotsRequest;

    private List<Appointment> appointmentList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        UserDto userDto = userService.saveOrUpdate(UserDto.builder()
                .firstName("firstNameUserTest")
                .lastName("lastNameUserTest")
                .email("email@test.com")
                .password("123")
                .username("test")
                .phoneNumber("09129231440")
                .enabled(true)
                .build());

        doctorDto = doctorService.saveOrUpdate(DoctorDto.builder()
                .userId(userDto.getId())
                .specialization("specialization")
                .startWorkTime("07:00")
                .endWorkTime("12:00")
                .build());


        slotsRequest = new SlotsRequest();
        slotsRequest.setDoctorId(doctorDto.getUserId());
        slotsRequest.setFromTime("07:00");
        slotsRequest.setToTime("12:00");
        slotsRequest.setCurrentDate("2024-09-14");
        appointmentList = appointmentService.setSlots(slotsRequest);


    }


    @Test
    @Order(0)
    void availableAppointment() throws Exception {
        var res = mockMvc.perform(get("/api/appointment/available")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("doctorId", doctorDto.getUserId().toString())
                        .param("currentDate", slotsRequest.getCurrentDate())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()", Matchers.greaterThan(1)))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    @Order(0)
    void availableAppointment_emptyList() throws Exception {
        var res = mockMvc.perform(get("/api/appointment/available")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("doctorId", doctorDto.getUserId().toString())
                        .param("currentDate", "2000-09-11")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()", Matchers.lessThan(1)))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void takenAppointment_isOk() throws Exception {

        var appointmentRequest = AppointmentRequest.builder()
                .appointmentId(appointmentList.get(0).getId())
                .name("شایان")
                .phoneNumber("09129231440")
                .build();

        var res = mockMvc.perform(put("/api/appointment/taken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appointmentRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(appointmentList.get(0).getId()))
                .andReturn()
                .getResponse()
                .getContentAsString();
        log.info(res);
    }

    @Test
    void takenAppointment_isNotFound() throws Exception {

        var appointmentRequest = AppointmentRequest.builder()
                .appointmentId(2024L)
                .name("شایان")
                .phoneNumber("09129231440")
                .build();

        var res = mockMvc.perform(put("/api/appointment/taken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appointmentRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("appointment.not.found.exception"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        log.info(res);
    }


    @Test
    void takenAppointment_isNotAcceptable() throws Exception {

        var appointmentRequest = AppointmentRequest.builder()
                .appointmentId(appointmentList.get(0).getId())
                .name("شایان")
                .phoneNumber("09129231440")
                .build();

        var res = mockMvc.perform(put("/api/appointment/taken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appointmentRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(appointmentList.get(0).getId()))
                .andReturn()
                .getResponse()
                .getContentAsString();

        var res2 = mockMvc.perform(put("/api/appointment/taken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appointmentRequest)))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.error").value("appointment.status.isNot.open.exception"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        log.info(res);
    }

    @Test
    public void takenAppointment_ConcurrentRequests_RaceCondition() throws Exception {

        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            try {
                appointmentService.deleteAppointment(appointmentList.get(2).getId());
                System.out.println("future1 runAsync ");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }, taskExecutor);

        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
            try {
                var appointmentRequest = AppointmentRequest.builder()
                        .appointmentId(appointmentList.get(6).getId())
                        .name("شایان")
                        .phoneNumber("09129231440")
                        .build();
                var res = appointmentService.takenAppointment(appointmentRequest);
                System.out.println("future2 runAsync ");
                log.info(res);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, taskExecutor);

        CompletableFuture.allOf(future1, future2).join();

    }


    @Test
    void getUserAppointments_Size_GreaterThan0() throws Exception {
        var appointmentRequest = AppointmentRequest.builder()
                .appointmentId(appointmentList.get(4).getId())
                .name("حسن")
                .phoneNumber("09120000000")
                .build();
        var takenAppointment = appointmentService.takenAppointment(appointmentRequest);

        var res = mockMvc.perform(get("/api/appointment/getUserAppointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("phoneNumber","09120000000")
                        .param("currentDate", slotsRequest.getCurrentDate())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()", Matchers.greaterThan(0)))
                .andReturn()
                .getResponse()
                .getContentAsString();

    }

    @Test
    void getUserAppointments_isEmptyList() throws Exception {

        var appointmentRequest = AppointmentRequest.builder()
                .appointmentId(appointmentList.get(5).getId())
                .name("ممد")
                .phoneNumber("09120000000")
                .build();
        var takenAppointment = appointmentService.takenAppointment(appointmentRequest);

        var res = mockMvc.perform(get("/api/appointment/getUserAppointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("phoneNumber","09121111111")
                        .param("currentDate", slotsRequest.getCurrentDate())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()", Matchers.lessThan(1)))
                .andReturn()
                .getResponse()
                .getContentAsString();

    }
}