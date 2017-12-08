package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.data.JobFieldData;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        Job theJob = jobData.findById(id);
        model.addAttribute("job", theJob);


        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(@ModelAttribute @Valid JobForm jobForm, Errors errors, Model model) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.
        if (errors.hasErrors()) {
            model.addAttribute("jobForm", jobForm);
            return "new-job";
        }


        int employerId = jobForm.getEmployerId();
        int locationId = jobForm.getLocationId();
        int coreCompetencyId = jobForm.getCoreCompetencyId();
        int positionTypeId = jobForm.getPositionTypeId();
        String name = jobForm.getName();

        Employer employer = jobData.getEmployers().findById(employerId);
        Location location = jobData.getLocations().findById(locationId);
        PositionType positionType = jobData.getPositionTypes().findById(positionTypeId);
        CoreCompetency coreCompetency = jobData.getCoreCompetencies().findById(coreCompetencyId);
        Job newJob = new Job(name, employer, location, positionType, coreCompetency);
        jobData.add(newJob);

        return "redirect:http://localhost:8080/job?id=" + newJob.getId();

    }
}
