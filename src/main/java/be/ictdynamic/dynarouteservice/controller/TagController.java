package be.ictdynamic.dynarouteservice.controller;

import be.ictdynamic.dynarouteservice.DynaRouteServiceConstants;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RestController
public class TagController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TagController.class);

    @ApiOperation(value = "Test method to verify whether String has matching tags.")
    @RequestMapping(value = "/tags",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity isTagValid(@RequestParam(value = "stringOfTags", required = true, defaultValue = "[({}){[{}]}]") String stringOfTags) {
        LOGGER.info(DynaRouteServiceConstants.LOG_STARTING + " stringOfTags = {}", stringOfTags);

        boolean isStringOfTags = true;

        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("{", "}"));
        tags.add(new Tag("[", "]"));
        tags.add(new Tag("(", ")"));

        List<String> tagsToBeClosed = new ArrayList<>();


        for (int i=0; i<stringOfTags.length(); i++) {
            String tag = stringOfTags.substring(i,i+1);
            if (tags.stream().anyMatch(openingTag -> openingTag.getOpeningString().equals(tag))) {
                tagsToBeClosed.add(tag);
            }
            else {
                if (tags.stream().anyMatch(closingTag -> closingTag.getClosingString().equals(tag))) {
                    // we've got a closing tag so let's verify the content of tagsToBeClosed. The last entry should match our opening tag
                    String openingTag = this.findOpeningTagForClosingTag(tag, tags);
                    if (CollectionUtils.isEmpty(tagsToBeClosed) || !tagsToBeClosed.get(tagsToBeClosed.size() - 1).equals(openingTag)) {
                        // our last tag to be closed does not match the closing tag we are processing, we can stop processing
                        isStringOfTags = false;
                        break;
                    } else {
                        // our opening tag has been closed so we can remove it from our tagsToBeClosed list
                        tagsToBeClosed.remove(tagsToBeClosed.size() - 1);
                    }
                }
                else {
                    // we've got an invalid String, we can stop processing
                    isStringOfTags = false;
                    break;
                }
            }
        }

        Date date = new Date();

        LocalDateTime localDateTime  = LocalDateTime.now();
        LocalDate localDate1 = LocalDate.of(2016, 1, 1);

        if (!localDate1.isBefore(localDateTime.toLocalDate())) {
            LOGGER.info("We are in 2016 or later");
        }

        LOGGER.info(DynaRouteServiceConstants.LOG_ENDING + " stringOfTags = {}, result = {} ", stringOfTags, isStringOfTags);
        return ResponseEntity.ok(new DummyResponse(isStringOfTags));
    }

    private String findOpeningTagForClosingTag(String closingTag, Set<Tag> tags) {
        for (Tag tag : tags) {
            if (tag.getClosingString().equals(closingTag)) {
                return tag.getOpeningString();
            }
        }
        // this should never happen
        return null;
    }

    private class Tag {
        private String openingString;
        private String closingString;

        public Tag(String openingString, String closingString) {
            this.openingString = openingString;
            this.closingString = closingString;
        }

        public String getOpeningString() {
            return openingString;
        }

        public void setOpeningString(String openingString) {
            this.openingString = openingString;
        }

        public String getClosingString() {
            return closingString;
        }

        public void setClosingString(String closingString) {
            this.closingString = closingString;
        }
    }

    private class DummyResponse {
        private boolean stringValid;

        public DummyResponse(boolean stringValid) {
            this.stringValid = stringValid;
        }

        public boolean isStringValid() {
            return stringValid;
        }

        public void setStringValid(boolean stringValid) {
            this.stringValid = stringValid;
        }
    }
}
