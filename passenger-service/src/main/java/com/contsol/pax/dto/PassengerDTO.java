package com.contsol.pax.dto;

import java.util.UUID;

public enum PassengerDTO {
    ;

    private interface Id {
        /**
         * The Id of the passenger onboard the journey vehicle
         */
        UUID getId();
    }

    private interface Survived {
        boolean hasSurvived();
    }

    private interface Name {
        String getName();
    }

    private interface Sex {
        String getSex();
    }

    private interface Age {
        int getAge();
    }

    private interface SiblingsOrSpousesAboard {
        int getSiblingsOrSpousesAboard();
    }

    private interface ParentsOrChildrenAboard {
        int getParentsOrChildrenAboard();
    }

    private interface Fare {
        double getFare();
    }

    public enum Request {
        ;

        public static class Create implements Survived, Name, Sex, Age, SiblingsOrSpousesAboard, ParentsOrChildrenAboard, Fare {

            boolean survived;
            String name;
            String sex;
            int age;
            int siblingsOrSpousesAboard;
            int parentsOrChildrenAboard;
            double fare;

            public Create(
                    boolean survived,
                    String name,
                    String sex,
                    int age,
                    int siblingsOrSpousesAboard,
                    int parentsOrChildrenAboard,
                    double fare) {
                this.survived = survived;
                this.name = name;
                this.sex = sex;
                this.age = age;
                this.siblingsOrSpousesAboard = siblingsOrSpousesAboard;
                this.parentsOrChildrenAboard = parentsOrChildrenAboard;
                this.fare = fare;
            }

            @Override
            public boolean hasSurvived() {
                return this.survived;
            }

            @Override
            public String getName() {
                return this.name;
            }

            @Override
            public String getSex() {
                return this.sex;
            }

            @Override
            public int getAge() {
                return this.age;
            }

            @Override
            public int getSiblingsOrSpousesAboard() {
                return this.siblingsOrSpousesAboard;
            }

            @Override
            public int getParentsOrChildrenAboard() {
                return this.parentsOrChildrenAboard;
            }

            @Override
            public double getFare() {
                return this.fare;
            }
        }
    }

    public enum Response {;
        public static class Public implements Id, Survived, Name, Sex, Age, SiblingsOrSpousesAboard, ParentsOrChildrenAboard, Fare {
            UUID id;
            boolean survived;
            String name;
            String sex;
            int age;
            int siblingsOrSpousesAboard;
            int parentsOrChildrenAboard;
            double fare;

            public Public(UUID id,
                          boolean survived,
                          String name,
                          String sex,
                          int age,
                          int siblingsOrSpousesAboard,
                          int parentsOrChildrenAboard,
                          double fare) {
                this.id = id;
                this.survived = survived;
                this.name = name;
                this.sex = sex;
                this.age = age;
                this.siblingsOrSpousesAboard = siblingsOrSpousesAboard;
                this.parentsOrChildrenAboard = parentsOrChildrenAboard;
                this.fare = fare;
            }

            @Override
            public UUID getId() {
                return this.id;
            }

            @Override
            public boolean hasSurvived() {
                return this.survived;
            }

            @Override
            public String getName() {
                return this.name;
            }

            @Override
            public String getSex() {
                return this.sex;
            }

            @Override
            public int getAge() {
                return this.age;
            }

            @Override
            public int getSiblingsOrSpousesAboard() {
                return this.siblingsOrSpousesAboard;
            }

            @Override
            public int getParentsOrChildrenAboard() {
                return this.parentsOrChildrenAboard;
            }

            @Override
            public double getFare() {
                return this.fare;
            }
        }
    }
}
