package ru.stqa.pft.mantis.appmanager;

import biz.futureware.mantis.rpc.soap.client.*;
import ru.stqa.pft.mantis.model.Issue;
import ru.stqa.pft.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.*;
import java.util.stream.Collectors;

public class SoapHelper {
    private ApplicationManager app;

    public SoapHelper(ApplicationManager app) {
        this.app = app;
    }

    public Set<Project> getProjects() throws MalformedURLException, ServiceException, RemoteException {
        MantisConnectPortType mc = getMantisConnect();
        ProjectData[] projects = mc.mc_projects_get_user_accessible(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword"));
        return Arrays.stream(projects).map((p) -> new Project().withId(p.getId().intValue()).withName(p.getName())).collect(Collectors.toSet());
    }

    private MantisConnectPortType getMantisConnect() throws ServiceException, MalformedURLException {
        MantisConnectPortType mc = new MantisConnectLocator()
                .getMantisConnectPort(
                        new URL(app.getProperty("web.soapUrl")));
        return mc;
    }

    public Issue addIssue(Issue issue) throws MalformedURLException, ServiceException, RemoteException {
        MantisConnectPortType mc = getMantisConnect();
        String[] categories = mc.mc_project_get_categories(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword"), BigInteger.valueOf(issue.getProject().getId()));
        IssueData issueData = new IssueData();
        issueData.setSummary(issue.getSummary());
        issueData.setDescription(issue.getDescription());
        issueData.setProject(new ObjectRef(BigInteger.valueOf(issue.getProject().getId()), issue.getProject().getName()));
        issueData.setCategory(categories[0]);
        BigInteger issueId = mc.mc_issue_add(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword"), issueData);
        IssueData createdIssueData = mc.mc_issue_get(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword"), issueId);
        return new Issue()
                .withId(createdIssueData.getId().intValue())
                .withSummary(createdIssueData.getSummary())
                .withDescription(createdIssueData.getDescription())
                .withProject(new Project()
                        .withId(createdIssueData.getProject().getId().intValue())
                        .withName(createdIssueData.getProject().getName()));
    }


    public Issue getIssue(int issueId) throws MalformedURLException, ServiceException, RemoteException {
        MantisConnectPortType mc = getMantisConnect();
        IssueData issueData = mc.mc_issue_get(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword"), BigInteger.valueOf(issueId));
        return new Issue()
                .withId(issueData.getId().intValue())
                .withDescription(issueData.getDescription())
                .withSummary(issueData.getSummary())
                .withProject(new Project()
                        .withId(issueData.getProject().getId().intValue())
                        .withName(issueData.getProject().getName()))
                .withResolution(issueData.getResolution().getName());
    }

    public Set<Issue> allIssues() throws MalformedURLException, ServiceException, RemoteException {
        MantisConnectPortType mc = getMantisConnect();
        ProjectData[] projects = mc.mc_projects_get_user_accessible(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword"));
        int filterId = 0;
        List<Issue> issues = new ArrayList<>();
        FilterData[] filters = mc.mc_filter_get(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword"), Arrays.stream(projects).iterator().next().getId());
        for (FilterData filter : filters) {
            if (filter.getName().equals("testFilter2")) filterId = filter.getId().intValue();
        }
        IssueData[] issueDataList = mc.mc_filter_get_issues(app.getProperty("web.adminLogin")
                , app.getProperty("web.adminPassword")
                , Arrays.stream(projects).iterator().next().getId(), BigInteger.valueOf(filterId), BigInteger.valueOf(1), BigInteger.valueOf(-1));
        for (IssueData issuedata : issueDataList) {
            Issue issue = new Issue().withId(issuedata.getId().intValue())
                    .withDescription(issuedata.getDescription())
                    .withSummary(issuedata.getSummary())
                    .withProject(new Project().withId(Arrays.stream(projects).iterator().next().getId().intValue())
                            .withName(Arrays.stream(projects).iterator().next().getName()))
                    .withResolution(issuedata.getResolution().getName());
            issues.add(issue);

        }
        return new HashSet<>(issues);
    }
}
