package com.kaano8.androidcore.com.kaano8.androidcore.recyclerview.repository

import com.kaano8.androidcore.com.kaano8.androidcore.recyclerview.data.Actor


object ActorRepository {
    private fun getOriginalActorList(): List<Actor> {
        val actors: MutableList<Actor> = ArrayList()
        actors.add(Actor(1, "Jack Nicholson", 10, 1937))
        actors.add(Actor(2, "Marlon Brando", 9, 1924))
        actors.add(Actor(3, "Robert De Niro", 8, 1943))
        actors.add(Actor(4, "Al Pacino", 7, 1940))
        actors.add(Actor(5, "Daniel Day-Lewis", 6, 1957))
        actors.add(Actor(6, "Dustin Hoffman", 5, 1937))
        actors.add(Actor(7, "Tom Hanks", 4, 1956))
        actors.add(Actor(8, "Anthony Hopkins", 3, 1937))
        actors.add(Actor(9, "Paul Newman", 2, 1925))
        actors.add(Actor(10, "Denzel Washington", 1, 1954))
        return actors
    }

    // Descending order
    fun getActorListSortedByRating(): List<Actor> {
        return getOriginalActorList().sortedWith { o1, o2 -> o2.rating - o1.rating }
    }

    fun getActorListSortedByName(): List<Actor> {
        return getOriginalActorList().sortedWith { o1, o2 -> o1.name.compareTo(o2.name) }
    }

    fun getActorListSortedByYearOfBirth(): List<Actor> {
        return getOriginalActorList().sortedWith { o1, o2 -> o1.dob - o2.dob }
    }
}