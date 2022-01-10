ls -l grading/*.test | awk '{print $5,$9}' | sort -t' ' -k1 -n -r
