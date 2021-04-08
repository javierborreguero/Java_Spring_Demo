package com.javier.spring.controllers;

import javax.validation.Valid;

import com.javier.spring.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.javier.spring.service.EmployeeService;

@Controller
public class EmployeeController {

    @Autowired
    public EmployeeService service;

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
    public String addNewEmployee(@Valid @ModelAttribute("empleadoForm") Employee newEmployee, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "form";
        } else {
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

}
