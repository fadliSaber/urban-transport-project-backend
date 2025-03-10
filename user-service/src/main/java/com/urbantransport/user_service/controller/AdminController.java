package com.urbantransport.user_service.controller;

import com.urbantransport.user_service.model.Admin;
import com.urbantransport.user_service.service.AdminService;
import java.nio.file.AccessDeniedException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// @CrossOrigin(origins = "${spring.graphql.cors.allowed-origins}")
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

  private final AdminService adminService;

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping("/admins")
  List<Admin> getAllAdmins() throws AccessDeniedException {
    return adminService.getAll();
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PutMapping("/upadmin/{id}")
  Admin updateAdmin(@PathVariable String id, @RequestBody Admin newadmin)
    throws AccessDeniedException {
    return adminService.updateAdmin(id, newadmin);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @DeleteMapping("/deladmin/{id}")
  void deleteAdmin(@PathVariable String id) throws AccessDeniedException {
    adminService.deleteAdmin(id);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping("/getadmin/{id}")
  Admin getAdmin(@PathVariable String id) throws AccessDeniedException {
    return adminService.getAdmin(id);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping("/{email}")
  Admin getAdminByEmail(@PathVariable String email) {
    return adminService.getAdminByEmail(email);
  }
}
