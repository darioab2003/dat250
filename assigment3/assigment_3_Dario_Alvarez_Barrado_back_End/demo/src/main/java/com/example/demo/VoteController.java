package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/votes")
public class VoteController {

    @Autowired
    private PollManager pollManager;

    @GetMapping
    public ResponseEntity<Map<String, Vote>> getAllVotes() {
        return ResponseEntity.ok(pollManager.getVotes());
    }

    @PostMapping
    public ResponseEntity<Vote> createVote(@RequestBody Vote vote) {
        String id = "vote-" + (pollManager.getVotes().size() + 1);
        vote.setId(id);
        pollManager.addVote(id, vote);
        return ResponseEntity.ok(vote);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vote> getVote(@PathVariable String id) {
        Vote vote = pollManager.getVotes().get(id);
        if (vote != null) {
            return ResponseEntity.ok(vote);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vote> updateVote(@PathVariable String id, @RequestBody Vote updatedVote) {
        pollManager.getVotes().put(id, updatedVote);
        return ResponseEntity.ok(updatedVote);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVote(@PathVariable String id) {
        pollManager.getVotes().remove(id);
        return ResponseEntity.noContent().build();
    }
}
