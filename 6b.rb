# For each input number, count down to 0 then restart at 6 and add an 8 to the list. Do this (256) times, then return how long the list will be.
# I know there's probably a mathematical formula for this but I'm doing dynamic programming

input_file = "inputs/6.txt"
input = File.open(input_file, &:readline).split(",").map(&:to_i)

days = 256
$cycle_length = 7
$counted = {}

def final_length(num, days)
  if num >= days
    unless $counted.key?(days)
      $counted[days] = {}
    end
    $counted[days][num] = 1
  end
  if $counted.key?(days)
    if $counted[days].key?(num)
      return $counted[days][num]
    end
  end
  count = 1
  countdown = days - (num + 1)
  while countdown >= 0
    count += final_length(8, countdown)
    countdown -= $cycle_length
  end

  until num < 0
    unless $counted.key?(days)
      $counted[days] = {}
    end
    $counted[days][num] = count
    num -= 1
    days -= 1
  end
  return count
end

total_count = 0
input.each do |counter|
  total_count += final_length(counter, days)
end

p total_count
