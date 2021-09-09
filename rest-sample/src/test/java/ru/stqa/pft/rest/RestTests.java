package ru.stqa.pft.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class RestTests {

    @Test
    public void testCreateIssue() throws IOException {
        Set<Issue> oldIssues = getIssues();
        Issue newIssue = new Issue().withSubject("Test Issue").withDescription("New test issue");
        int issueId = createIssue(newIssue);
        Set<Issue> newIssues = getIssues();
        oldIssues.add(newIssue.withId(issueId));
        assertEquals(newIssues, oldIssues);
    }

    private Set<Issue> getIssues() throws IOException {
        String json = getExecutor().execute(Request.Get("https://bugify.stqa.ru/api/issues.json")).returnContent().asString();
        JsonElement parsed = JsonParser.parseString(json);
        JsonElement issues = parsed.getAsJsonObject().get("issues");
        return new Gson().fromJson(issues, new TypeToken<Set<Issue>>() {
        }.getType());

    }

    private Executor getExecutor() {
        return Executor.newInstance().auth("288f44776e7bec4bf44fdfeb1e646490", "");
    }

    private int createIssue(Issue newIssue) throws IOException {
        String json = getExecutor().execute(Request.Post("https://bugify.stqa.ru/api/issues.json")
                .bodyForm(new BasicNameValuePair("subject", newIssue.getSubject()),
                        new BasicNameValuePair("description", newIssue.getDescription())))
                .returnContent().asString();
        JsonElement parsed = JsonParser.parseString(json);
        return parsed.getAsJsonObject().get("issue_id").getAsInt();
    }

    private Issue getIssue(int issueId) throws IOException {
        String json = getExecutor().execute(Request.Get("https://bugify.stqa.ru/api/issues/"+ issueId + ".json")).returnContent().asString();
        JsonElement parsed = JsonParser.parseString(json);
        JsonElement issue = parsed.getAsJsonObject().get("issues");
        Set<Issue> oneIssue = new Gson().fromJson(issue, new TypeToken<Set<Issue>>() {}.getType());
        Issue gettedissue = oneIssue.iterator().next();
        return gettedissue;
    }

    private void closeIssue(int issueId) throws IOException {
         getExecutor()
                .execute(Request.Post("https://bugify.stqa.ru/api/issues/"+ issueId + ".json")
                        .bodyForm(new BasicNameValuePair("method", "update"),
                new BasicNameValuePair("issue[state]", "3")))
                .returnContent().asString();
        assertEquals(getIssue(issueId).getState_name(), "Closed");
    }

    private void updateDescriptionIssue(int issueId) throws IOException {
            getExecutor()
                .execute(Request.Post("https://bugify.stqa.ru/api/issues/"+ issueId + ".json")
                        .bodyForm(new BasicNameValuePair("method", "update"),
                                new BasicNameValuePair("issue[description]", "Update Description")))
                .returnContent().asString();
        assertEquals(getIssue(issueId).getDescription(), "Update Description");
    }


    public boolean isIssueOpen(int issueId) throws IOException {
        Issue issue = getIssue(issueId);
        if (issue.getState_name().equals("Closed")) return false;
        else return true;
    }

    public void skipIfNotFixed(int issueId) throws IOException {
        if (isIssueOpen(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }


    @Test
    public void runningAutotestIfIssueClosed() throws IOException {
        Set<Issue> allIssues = getIssues();
        Issue issue = allIssues.iterator().next();
        System.out.println("ID= " + issue.getId());
        if (isIssueOpen(issue.getId())) closeIssue(issue.getId());
        skipIfNotFixed(issue.getId());
        System.out.println("выполняется код автотеста");
        updateDescriptionIssue(issue.getId());
    }

    @Test
    public void runningAutotestIfIssueOpen() throws IOException {
        Set<Issue> allIssues = getIssues();
        Issue issue = allIssues.iterator().next();
        System.out.println("ID= " + issue.getId());
        skipIfNotFixed(issue.getId());
        System.out.println("выполняется код автотеста");
    }

}
