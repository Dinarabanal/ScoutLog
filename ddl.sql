
        CREATE TABLE IF NOT EXISTS `Scout` (
            `scout_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
            `last_name` TEXT,
            `first_name` TEXT,
             `rank` TEXT)
        );

        CREATE UNIQUE INDEX
            `index_Scout_last_name_first_name`
              ON `Scout` (`last_name`, `first_name`);

        CREATE TABLE IF NOT EXISTS `Badges` (
              `badge_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
              `badge_name` TEXT NOT NULL COLLATE NOCASE,
              `image_link` TEXT);

         CREATE  INDEX `index_Badge_badge_name`
              ON `Badge` (`badge_name`);
