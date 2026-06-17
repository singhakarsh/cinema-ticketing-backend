package com.example.demo.model;

public class AnalyticsSummaryDTO {
    private long totalMovies;
    private long totalShowtimes;
    private double totalRevenue;
    private double occupancyRate;

    public AnalyticsSummaryDTO(long totalMovies, long totalShowtimes, double totalRevenue, double occupancyRate) {
        this.totalMovies = totalMovies;
        this.totalShowtimes = totalShowtimes;
        this.totalRevenue = totalRevenue;
        this.occupancyRate = occupancyRate;
    }

    // Getters and Setters
    public long getTotalMovies() {
        return totalMovies;
    }

    public void setTotalMovies(long totalMovies) {
        this.totalMovies = totalMovies;
    }

    public long getTotalShowtimes() {
        return totalShowtimes;
    }

    public void setTotalShowtimes(long totalShowtimes) {
        this.totalShowtimes = totalShowtimes;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public double getOccupancyRate() {
        return occupancyRate;
    }

    public void setOccupancyRate(double occupancyRate) {
        this.occupancyRate = occupancyRate;
    }
}