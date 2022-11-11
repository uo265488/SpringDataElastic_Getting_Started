package co.empathy.academy.search.helpers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RatingsDto {

    public String id;
    public double averageRating;
    public int numVotes;

}
