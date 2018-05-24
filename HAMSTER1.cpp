#include <iostream>
#include <cmath>
using namespace std;
#define eps 0.000000001

double points (double u, double k1, double k2, double rad) {
	return k1*u*u*sin(2*rad)/10 + k2*u*u*sin(rad)*sin(rad)/(20);
}
    
int main() {
	int t; cin>>t;
	while (t-->0) {
	    int u, k1, k2; cin>>u>>k1>>k2;
	    double low = 0, high = M_PI/2;
	    while (low<=high && high-low>eps) {
			double mid1 = low + (high-low)/3, mid2 = high - (high-low)/3;
			if (points(u, k1, k2, mid1) == points(u, k1, k2, mid2)) {
				if (mid1 == mid2) break;
				else {low = mid1; high = mid2;} 
			}
			else if (points(u, k1, k2, mid1) <= points(u, k1, k2, mid2)) low = mid1;
			else high = mid2;
	    }
	    printf("%.3f %.3f\n", low, points(u, k1, k2, low));
	}
	return 0;
}
