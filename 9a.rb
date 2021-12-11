# Find all points where the 4 cardinal directions are all higher values, return sum(1+point) for all points

input_file = "inputs/9.txt"
input = []

File.open(input_file) do |f|
  input = f.read.split("\n").map{ |n| n.split("").map(&:to_i) }
end

sum_risks = 0

p input

input.each_with_index do |row, row_idx|
  row.each_with_index do |num, col_idx|
    lowest = true
    unless row_idx == 0
      if input[row_idx-1][col_idx] <= num
        lowest = false
      end
    end
    unless col_idx == 0
      if input[row_idx][col_idx-1] <= num
        lowest = false
      end
    end
    unless row_idx == input.length - 1
      if input[row_idx+1][col_idx] <= num
        lowest = false
      end
    end
    unless col_idx == row.length - 1
      if input[row_idx][col_idx+1] <= num
        lowest = false
      end
    end
    next unless lowest
    p "#{num} row:#{row_idx} col:#{col_idx}"
    sum_risks += 1 + num
  end
end

p sum_risks
