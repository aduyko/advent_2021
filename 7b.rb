# Find a depth such that given a list of depths, depth x is in aggregate the least distant AND distance is measured as the sum from 1 to n where n is abs(depth - x)
# so if one of the depths on the list is 16 and the aggregate is 5, distance would be 1 + 2 + 3 + 4 + ... + 11
# Mean comes close but no cigar! Just brute forced it.

input_file = "inputs/7.txt"
input = File.open(input_file, &:readline).split(",").map(&:to_i).sort

sums = {}

(input.min..input.max).each do |i|
  sum = 0
  input.each do |n|
    diff = (i - n).abs
    sum += diff*(diff+1)/2
  end
  sums[i] = sum
end

p sums.values.min
