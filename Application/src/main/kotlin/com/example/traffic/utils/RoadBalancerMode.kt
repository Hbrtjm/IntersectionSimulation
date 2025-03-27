package com.example.traffic.utils

/**
 *
 * Defines possible states of the road balancer.
 *
 * The RoadBalancer can work in one of the following states:
 *  - RANDOM: Cars will approach matching starting roads that align with their destination at random.
 *  - BALANCE: Cars will favor roads with lesser traffic that match their preferences
 *
 */
enum class RoadBalancerMode {
    RANDOM, BALANCE
}