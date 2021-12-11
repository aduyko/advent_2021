# Figure out which bingo board wins last
# Input is first a list of bingo numbers in the order they are pulled, then 5x5 grid of numbers representing a bingo card
# Sum and points are used to answer the problem

input_file = "inputs/4.txt"
draws = {}
boards = []
playing_boards = []

File.open(input_file) do |f|
  draws = f.gets.chomp.split(",").map(&:to_i).to_h { |n| [n,[]] }
  while line = f.gets
    line = line.chomp
    board = { :sum => 0, :rows => [] }
    until board[:rows].length == 5
      unless line.empty?
        row = line.split.map(&:to_i)
        board[:rows] << row
        board[:sum] += row.sum
        row.each do |n|
          draws[n] << boards.length
        end
      end
      line = f.gets
      line = line.chomp if line
    end
    boards << board
    playing_boards << boards.length - 1
  end
end

done = false
points = 0
last_board = 0
draws.each do |draw, board_idxs|
  board_idxs.each do |board_idx|
    board = boards[board_idx]
    board[:sum] -= draw
    board[:rows].each_with_index do |row|
      row.each_with_index do |n,idx|
        row[idx] = nil if n == draw
      end
    end
    if board[:rows].map { |r| r.all?(&:nil?) }.include?(true) || board[:rows].transpose.map { |r| r.all?(&:nil?) }.include?(true)
      if playing_boards.include?(board_idx)
        playing_boards.delete(board_idx)
        if playing_boards.length == 0
          last_board = board_idx
          points = draw * board[:sum]
          done = true
          break
        end
      end
    end
  end
  break if done
end

p points
