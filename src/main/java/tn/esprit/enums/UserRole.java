    package tn.esprit.enums;

    public enum UserRole {
        ROLE_MOTHER("[\"ROLE_MOTHER\"]"),
        ROLE_ADMIN("[\"ROLE_ADMIN\"]");

        private final String roleName;

        UserRole(String roleName) {
            this.roleName = roleName;
        }

        public String getRoleName() {
            return roleName;
        }
    }
