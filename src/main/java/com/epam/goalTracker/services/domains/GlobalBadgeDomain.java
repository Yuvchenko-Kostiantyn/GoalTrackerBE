package com.epam.goalTracker.services.domains;

import java.util.List;

/**
 * Global badge domain
 *
 * @author Fazliddin Makhsudov
 * @version 1.0
 * @date 21.12.20 14:53
 */
public class GlobalBadgeDomain {

    private long id;
    private String name;
    private String url;
    private long scores;
    private List<PersonalBadgeDomain> personalBadges;

}
