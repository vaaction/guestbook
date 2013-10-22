package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;
import play.data.validation.*;

@Entity
public class Post extends Model {

    @Required
	@MaxSize(100)
    public String title;

    @Required
    public Date postedDate;

    @Lob
    @Required
    @MaxSize(10000)
    public String content;

    @Required
    @ManyToOne
    public User author;

	@OneToMany(cascade=CascadeType.ALL)
	public Set<Vote> votes;

    public Post(User author, String title, String content) {
		votes = new HashSet<Vote>();
        this.author = author;
        this.title = title;
        this.content = content;
        this.postedDate = new Date();
    }

	public Post vote(long userId, VoteStatus status) {
		Vote previousVote = Vote.find("byUserIdAndPost", userId, this).first();
		if (previousVote == null) {
			Vote newVote = new Vote(userId, this, status).save();
			votes.add(newVote);
			save();
		} else if (!previousVote.voteStatus.equals(status)) {
			if (!previousVote.voteStatus.equals(VoteStatus.NONE)) {
				previousVote.voteStatus = VoteStatus.NONE;
			} else {
				previousVote.voteStatus = status;
			}
			previousVote.save();
		}

		return this;
	}

	public long score() {
		int score = 0;
		for (Vote vote : votes) {
			if (vote.voteStatus.equals(VoteStatus.LIKE)) {
				score++;
			} else if (vote.voteStatus.equals(VoteStatus.UNLIKE)){
				score--;
			}
		}
		return score;
	}
}