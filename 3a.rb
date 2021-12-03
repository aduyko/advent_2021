# Calculate "gamma" (most common bit in each position for all input values) and "epsilon" (least common bit) and multiply their decimal values

input_file = "inputs/3.txt"
input = []

File.open(input_file) do |f|
  input = f.read.split("\n")
end

bit_sums = Array.new(input[0].length, 0)

input.each do |n|
  n.chars.each_with_index do |bit,idx|
    bit_sums[idx] += bit.to_i
  end
end

bit_sums.map! { |n| n >= input.length/2 ? 1 : 0 }

gamma = bit_sums.map { |n| n.to_s }.join.to_i(2)
epsilon = bit_sums.map { |n| (1-n).to_s }.join.to_i(2)

p gamma * epsilon
