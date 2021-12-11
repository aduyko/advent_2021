# Find the size of all "basins" (numbers surrounded by nines) and multiply the three largest ones together

input_file = "inputs/9.txt"
input = []
$basins = []

File.open(input_file) do |f|
  input = f.read.split("\n").map{ |n| n.split("").map{ |m| [m.to_i, -1] } }
end

def calculate_basin(grid,row_idx,col_idx,basin_idx)
  if grid[row_idx][col_idx][0] == 9
    grid[row_idx][col_idx][1] = -2
    return 0
  end

  grid[row_idx][col_idx][1] = basin_idx
  $basins[basin_idx] += 1

  unless row_idx == 0
    if grid[row_idx-1][col_idx][1] == -1
      calculate_basin(grid,row_idx-1,col_idx,basin_idx)
    end
  end
  unless col_idx == 0
    if grid[row_idx][col_idx-1][1] == -1
      calculate_basin(grid,row_idx,col_idx-1,basin_idx)
    end
  end
  unless row_idx == grid.length - 1
    if grid[row_idx+1][col_idx][1] == -1
      calculate_basin(grid,row_idx+1,col_idx,basin_idx)
    end
  end
  unless col_idx == grid[0].length - 1
    if grid[row_idx][col_idx+1][1] == -1
      calculate_basin(grid,row_idx,col_idx+1,basin_idx)
    end
  end
  return 0
end

input.each_with_index do |row, row_idx|
  row.each_with_index do |values, col_idx|
    num = values[0]
    basin = values[1]
    next if num == 9
    next if basin != -1
    $basins << 0
    calculate_basin(input,row_idx,col_idx,$basins.length - 1)
  end
end

p $basins.max(3).inject(:*)
