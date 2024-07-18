#pragma once

double vec_sum(double* arr, int n);
double vec_min(double* arr, int n);
double vec_max(double* arr, int n);
double vec_prod(double* arr, int n);

double vec_mean(double* arr, int n);
double vec_var(double* arr, int n);
double vec_std(double* arr, int n);

void vec_coerce(double* arr, int n, double min, double max);
