package com.javier.spring.controllers;

import javax.validation.Valid;

import com.javier.spring.model.Employee;
import com.javier.spring.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.javier.spring.service.EmployeeService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@Controller
public class EmployeeController {

    @Autowired
    public EmployeeService service;
    @Autowired
    public StorageService storageService;

    @GetMapping({"/", "/empleado/list"})
    public String getAllEmployees(Model model) {
        model.addAttribute("listaEmpleados", service.findAll());
        return "list";
    }

    @GetMapping("/empleado/new")
    public String getNewEmployeeForm(Model model) {
        model.addAttribute("empleadoForm", new Employee());
        return "form";
    }

    @PostMapping("/empleado/new/submit")
    // @Valid para comprobar que los datos introducidos son validos. BindingResult sirve para saber si se ha producido algún error en el formulario
    public String addNewEmployee(@Valid @ModelAttribute("empleadoForm") Employee newEmployee, BindingResult bindingResult, @RequestParam("file") MultipartFile file) { //  @RequestParam para añadir los archivos

        if (bindingResult.hasErrors()) {
            return "form";
        } else {
            // Si el fichero tiene imagen se la añadimos al empleado
            if (!file.isEmpty()) {
                String image = storageService.store(file, newEmployee.getId());
                newEmployee.setImage(MvcUriComponentsBuilder.fromMethodName(EmployeeController.class, "serveFile", image).build().toUriString()); // Con "serveFile" e image, construimos una URI.
            }
            service.add(newEmployee);
            return "redirect:/empleado/list";
        }

    }

    @GetMapping("/empleado/edit/{id}")
    public String getEditForm(@PathVariable long id, Model model) {

        Employee employee = service.findById(id);
        if (employee != null) {
            model.addAttribute("empleadoForm", employee);
            return "form";
        } else {
            return "redirect:/empleado/new";
        }

    }

    @PostMapping("/empleado/edit/submit")
    public String editEmployee(@Valid @ModelAttribute("empleadoForm") Employee employee,
                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "form";
        } else {
            service.edit(employee);
            return "redirect:/empleado/list";
        }
    }

    // Guardar la imagen que subimos desde un fichero
    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String fileName) {
        Resource file = storageService.loadAsResource(fileName);
        return ResponseEntity.ok().body(file);
    }
}
