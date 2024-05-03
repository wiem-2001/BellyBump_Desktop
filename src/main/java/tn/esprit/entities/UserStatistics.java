package tn.esprit.entities;

public class UserStatistics {
    private int activeUsersCount;
    private int inactiveUsersCount;

    public UserStatistics(int activeUsersCount, int inactiveUsersCount) {
        this.activeUsersCount = activeUsersCount;
        this.inactiveUsersCount = inactiveUsersCount;
    }

    public int getActiveUsersCount() {
        return activeUsersCount;
    }

    public int getInactiveUsersCount() {
        return inactiveUsersCount;
    }
}
