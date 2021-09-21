package com.codegym.controller;

import com.codegym.model.Province;
import com.codegym.service.customerService.ICustomerService;
import com.codegym.service.provinceService.IProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class ProvinceController {


    @Autowired
    private IProvinceService provinceService;

    @GetMapping("/provinces")
    public ModelAndView listProvinces() {
        Iterable<Province> provinces = provinceService.findAll();
        ModelAndView mv = new ModelAndView("/province/list");
        mv.addObject("provinces", provinces);
        return mv;

    }

    @GetMapping("/create-province")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/province/create");
        modelAndView.addObject("province", new Province());
        return modelAndView;
    }

    @PostMapping("/create-province")
    public ModelAndView saveProvince(@ModelAttribute("province") Province province) {
        provinceService.save(province);
        ModelAndView mv = new ModelAndView("/province/create");
        mv.addObject("province", new Province());
        mv.addObject("message", "new province created successfully");
        return mv;
    }


    @GetMapping("/edit-province/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Optional<Province> province = provinceService.findById(id);
        if (province.isPresent()) {
            ModelAndView mv = new ModelAndView("/province/edit");
            mv.addObject("province", province.get());
            return mv;
        } else {
            ModelAndView mv = new ModelAndView("/error 404");
            return mv;
        }
    }

    @PostMapping("/edit-province/{id}")
    public ModelAndView updateProvince(@ModelAttribute("province") Province province) {

        provinceService.save(province);
        ModelAndView mv = new ModelAndView("/province/edit");
        mv.addObject("province", province);
        mv.addObject("massage", "Province updateed successfully");
        return mv;
    }

    @GetMapping("/delete-province/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {


        Optional<Province> province = provinceService.findById(id);
        if (province.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/province/delete");
            modelAndView.addObject("province", province.get());
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping ("/delete-province")
    public  String deleteProvince(@ModelAttribute("province") Province province){
            provinceService.remove(province.getId());
            return "redirect: provinces";

    }
}

