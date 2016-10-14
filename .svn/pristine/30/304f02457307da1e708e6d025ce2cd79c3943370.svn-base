DROP FUNCTION IF EXISTS sparrow_lc.s_f_getLayAge;
CREATE DEFINER=`phoelc`@`%` FUNCTION `s_f_getLayAge`(in_HouseBreedId int,in_ageType VARCHAR(10)) RETURNS int(11)
BEGIN
  DECLARE layedDay int;
  DECLARE layedWeek int;
  select min(b.day_age),min(b.week_age) into layedDay,layedWeek from s_b_layer_breed_detail b
           where 1=1 and b.house_breed_id = in_HouseBreedId and b.acc_lay_num <> 0 ;
  IF in_ageType = 'DayAge' THEN
     RETURN layedDay;
  ELSEIF in_ageType = 'WeekAge' THEN
    RETURN layedWeek;
  ELSE
    RETURN 0;
  END IF;
END;