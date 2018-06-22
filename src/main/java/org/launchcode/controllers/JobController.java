package org.launchcode.controllers;

import org.launchcode.models.Employer;
import org.launchcode.models.Job;
import org.launchcode.models.Location;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();


    /*@RequestMapping(value = "", method = RequestMethod.GET)
    public String jobById(@RequestParam(value = "id") int id, Model model) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        model.addAttribute("job", JobData.findById(id));
        Job job = JobData.findById(id);
        model.addAttribute(job);

        return "job-detail";
    }*/

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String jobById(@RequestParam(value = "id") int id, Model model) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        model.addAttribute("job", JobData.findById(id));
        Job job = JobData.findById(id);

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
            model.addAttribute(jobForm);
            return "new-job";
        }

        JobData jobData = JobData.getInstance();
        Job job = new Job();
        job.setName(jobForm.getName());
        job.setLocation(jobData.getLocations().findById(jobForm.getLocationId()));
        job.setEmployer(jobData.getEmployers().findById(jobForm.getEmployerId()));
        job.setCoreCompetency(jobData.getCoreCompetencies().findById(jobForm.getCoreCompetencyId()));
        job.setPositionType(jobData.getPositionTypes().findById(jobForm.getPositionTypeId()));

        JobData.add(job);
        int id = job.getId();

        model.addAttribute(job);
        model.addAttribute(id);
        return "redirect:/job/?id=" + id;

    }
}
