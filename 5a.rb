# Find how many points the defined vertical/horizontal lines intersect at at least once

input_file = "inputs/5.txt"
# No coords are > 1000 so start with a 1000x1000 grid of 0s
grid = Array.new(1000) { Array.new(1000, 0) }
input = []

File.open(input_file) do |f|
  # Turn input from "1,2 -> 2,3" into [[1,2], [2,3]]
  input = f.read.split("\n").map{ |n| n.split(" -> ").map { |m| m.split(",").map(&:to_i)} }
end

input.each do |coord_pair|
  if coord_pair[0][0] == coord_pair[1][0] || coord_pair[0][1] == coord_pair[1][1]
    until coord_pair[0][0] == coord_pair[1][0] && coord_pair [0][1] == coord_pair[1][1]
      grid[coord_pair[0][0]][coord_pair[0][1]] += 1
      if coord_pair[0][0] > coord_pair[1][0]
        coord_pair[0][0] -= 1
      end
      if coord_pair[0][0] < coord_pair[1][0]
        coord_pair[0][0] += 1
      end
      if coord_pair[0][1] > coord_pair[1][1]
        coord_pair[0][1] -= 1
      end
      if coord_pair[0][1] < coord_pair[1][1]
        coord_pair[0][1] += 1
      end
    end
    grid[coord_pair[0][0]][coord_pair[0][1]] += 1
  end
end

count_overlapping = 0
grid.each do |row|
  row.each do |cell|
    count_overlapping += 1 if cell > 1
  end
end

p count_overlapping
