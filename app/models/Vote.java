package models;

import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.*;

@Entity
public class Vote extends Model {

    @Required
    public Long userId;

	@ManyToOne
    @Required
    public Post post;

	@Required
	@Enumerated(EnumType.STRING)
	public VoteStatus voteStatus;

    public Vote(Long userId, Post post, VoteStatus voteStatus) {
        this.userId = userId;
        this.post = post;
		this.voteStatus = voteStatus;
    }
}
