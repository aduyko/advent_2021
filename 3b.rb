# Calculate "oxygen rating" (number with most common bit of all input values in each position starting from left) and "carbon rating" (least common bit) and multiply their decimal values

input_file = "inputs/3.txt"
input = []

File.open(input_file) do |f|
  input = f.read.split("\n").map { |n| n.chars }
end


def calculate_bit_sum(input,idx)
  bit_sum = 0
  input.each do |n|
    bit_sum += n[idx].to_i
  end
  bit_sum = bit_sum >= input.length/2.0 ? 1 : 0
end

oxygen_rating = input
carbon_rating = input

idx = 0
input[0].length.times do |idx|
  if oxygen_rating.length() > 1
    oxygen_criteria = calculate_bit_sum(oxygen_rating, idx)
    oxygen_rating = oxygen_rating.select { |bits| bits[idx] == oxygen_criteria.to_s }
  end

  if carbon_rating.length() > 1
    carbon_criteria = (1 - calculate_bit_sum(carbon_rating, idx)).to_s
    carbon_rating = carbon_rating.select { |bits| bits[idx] == carbon_criteria }
  end
end

oxygen = oxygen_rating[0].join.to_i(2)
carbon = carbon_rating[0].join.to_i(2)

p oxygen * carbon
